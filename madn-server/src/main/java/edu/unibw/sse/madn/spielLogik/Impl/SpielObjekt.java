package edu.unibw.sse.madn.spielLogik.Impl;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;
import edu.unibw.sse.madn.spielLogik.AnClientSendenSpiel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.unibw.sse.madn.base.FeldBesetztStatus.*;
import static edu.unibw.sse.madn.spielLogik.Impl.SpielMethoden.*;
import static java.lang.Thread.sleep;

public class SpielObjekt {
    private static final int BOT_WAIT_WUERFELN = 1000;
    private static final int BOT_WAIT_ZIEHEN = 2000;
    private static final int DELAY_WUERFELN = 10000;
    private static final int DELAY_SPIELZUG = 15000;
    private static final int DELAY_WAITING = 5000;

    private final SpielstatistikIntern spielStatistik;
    private final AnClientSendenSpiel anClient;
    private final SpielLogikImpl spielLogikImpl;

    private final String[] clients;
    private final String[] namen;
    private final FeldBesetztStatus[] felderVonSpieler;
    private final boolean[] finished;
    private int anzahlFinished = 0;
    private boolean sechser = false;

    private final int spielerAnzahl;
    private final FeldBesetztStatus[] boardState;
    private int aktiverSpieler = -1;
    private int zahlGewuerfelt = -5;
    private int anzahlWuerfeln = -1;

    private Timer timerWuerfeln = new Timer();
    private Timer timerZiehen = new Timer();
    private Timer timerWaiting = new Timer();

    // todo entfernen
    private final int[] wuerfel = new int[]{6,4,3,3,3,6,2,6,2,3,3,3,6,6,5};
    private int wuerfelCount = 0;

    protected SpielObjekt(SpielLogikImpl spielLogikImpl, AnClientSendenSpiel anClient, String[] sitzungen, int spielerUndBotsAnz) {
        this.spielLogikImpl = spielLogikImpl;
        this.anClient = anClient;
        spielerAnzahl = spielerUndBotsAnz;
        clients = sitzungen;

        namen = new String[spielerAnzahl];
        felderVonSpieler = new FeldBesetztStatus[spielerAnzahl];
        finished = new boolean[spielerAnzahl];

        if (spielerAnzahl == 3 || spielerAnzahl > 4) sechser = true;

        int botAnzahl = 0;
        for (int i = 0; i < spielerAnzahl; i++) {
            if (clients[i] == null) {
                botAnzahl++;
                namen[i] = "Bot" + botAnzahl;
            } else {
                namen[i] = clients[i];
            }
        }

        if (spielerAnzahl == 2) {
            felderVonSpieler[0] = FELD_SPIELER1;
            felderVonSpieler[1] = FELD_SPIELER3;
        } else if (spielerAnzahl == 3) {
            felderVonSpieler[0] = FELD_SPIELER1;
            felderVonSpieler[1] = FELD_SPIELER3;
            felderVonSpieler[2] = FELD_SPIELER5;
        } else {
            for (int i = 0; i < spielerAnzahl; i++) {
                felderVonSpieler[i] = switch (i) {
                    case 0 -> FELD_SPIELER1;
                    case 1 -> FELD_SPIELER2;
                    case 2 -> FELD_SPIELER3;
                    case 3 -> FELD_SPIELER4;
                    case 4 -> FELD_SPIELER5;
                    default -> FELD_SPIELER6;
                };
            }
        }
        spielStatistik = new SpielstatistikIntern();
        spielStatistik.namenSetzen(namen);
        boardState = feldFuellen(spielerAnzahl, sechser);

        Timer t = new Timer("startGame");
        t.schedule(new StartGame(), 200);

        displayNewState(boardState, null);
        aktivenSpielerSenden(-1);
    }

    public synchronized ZiehenRueckgabe submitMove(String sitzung, int from, int to) {
        int i = isInGame(sitzung);
        if (i == -1 || aktiverSpieler != i) return ZiehenRueckgabe.NICHT_DRAN;
        FeldBesetztStatus field = felderVonSpieler[aktiverSpieler];
        if (zahlGewuerfelt == -1) return ZiehenRueckgabe.NICHT_GEWUERFELT;
        if (!checkMoveValid(boardState, field, from, to, zahlGewuerfelt, sechser)) return ZiehenRueckgabe.ZUG_FEHLERHAFT;
        int[] prio = checkForPrioMove(boardState, field, zahlGewuerfelt, sechser);
        if (sechser) {
            if (prio != null && prio[0] < 24 && from > 23) return ZiehenRueckgabe.ZUG_FEHLERHAFT;
        } else {
            if (prio != null && prio[0] < 16 && from > 15) return ZiehenRueckgabe.ZUG_FEHLERHAFT;
        }
        return submitMoveIntern(from, to);
    }

    /**
     * move muss erlaubt sein, wird nur noch gesetzt + ggf Punish + Spieler informiert
     */
    private synchronized ZiehenRueckgabe submitMoveIntern(int from, int to) {
        if (aktiverSpieler < 0) return ZiehenRueckgabe.NICHT_DRAN;
        timerZiehen.cancel();
        timerWaiting.cancel();
        //System.out.println("Submit " + namen[aktiverSpieler] + ": " + from + " -> " + to);
        int[] changed = new int[]{from, to, -1, -1, -1}; // from, to, strafe in loch, jmd geschlagen, strafe Figur die weg
        // wenn geschlagen zurücksetzen
        FeldBesetztStatus fieldStateFrom = boardState[from];
        if (boardState[to] != FELD_LEER) {
            spielStatistik.incAndereGeschlagen(aktiverSpieler);
            for (int i = 0; i < spielerAnzahl; i++) {
                if (felderVonSpieler[i] == boardState[to]) {
                    spielStatistik.incGeschlagenWorden(i);
                    break;
                }
            }
            changed[3] = figurZurueckAufStartpositionen(to);
        }
        int[] bestrafung = new int[]{-1,-1};
        if (clients[aktiverSpieler] != null) {
            bestrafung = checkBestrafen(fieldStateFrom, from);
        }
        changed[2] = bestrafung[0];
        changed[4] = bestrafung[1];
        if (changed[2] != -1) spielStatistik.incPrioZugIgnoriert(aktiverSpieler);

        boardState[to] = fieldStateFrom;
        boardState[from] = FELD_LEER;

        checkFinished(fieldStateFrom);

        displayNewState(boardState, changed);
        nextMove(false);
        return (changed[2] == -1 ? ZiehenRueckgabe.ERFOLGREICH : ZiehenRueckgabe.BESTRAFT);
    }

    @SuppressWarnings("DuplicatedCode")
    private void checkFinished(FeldBesetztStatus fieldStateFrom) {
        if (sechser) {
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER1 && boardState[24] == FELD_SPIELER1 && boardState[25] == FELD_SPIELER1 && boardState[26] == FELD_SPIELER1 && boardState[27] == FELD_SPIELER1) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER2 && boardState[28] == FELD_SPIELER2 && boardState[29] == FELD_SPIELER2 && boardState[30] == FELD_SPIELER2 && boardState[31] == FELD_SPIELER2) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER3 && boardState[32] == FELD_SPIELER3 && boardState[33] == FELD_SPIELER3 && boardState[34] == FELD_SPIELER3 && boardState[35] == FELD_SPIELER3) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER4 && boardState[36] == FELD_SPIELER4 && boardState[37] == FELD_SPIELER4 && boardState[38] == FELD_SPIELER4 && boardState[39] == FELD_SPIELER4) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER5 && boardState[40] == FELD_SPIELER5 && boardState[41] == FELD_SPIELER5 && boardState[42] == FELD_SPIELER5 && boardState[43] == FELD_SPIELER5) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER6 && boardState[44] == FELD_SPIELER6 && boardState[45] == FELD_SPIELER6 && boardState[46] == FELD_SPIELER6 && boardState[47] == FELD_SPIELER6) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
        } else {
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER1 && boardState[16] == FELD_SPIELER1 && boardState[17] == FELD_SPIELER1 && boardState[18] == FELD_SPIELER1 && boardState[19] == FELD_SPIELER1) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER2 && boardState[20] == FELD_SPIELER2 && boardState[21] == FELD_SPIELER2 && boardState[22] == FELD_SPIELER2 && boardState[23] == FELD_SPIELER2) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER3 && boardState[24] == FELD_SPIELER3 && boardState[25] == FELD_SPIELER3 && boardState[26] == FELD_SPIELER3 && boardState[27] == FELD_SPIELER3) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
            if (!finished[aktiverSpieler] && fieldStateFrom == FELD_SPIELER4 && boardState[28] == FELD_SPIELER4 && boardState[29] == FELD_SPIELER4 && boardState[30] == FELD_SPIELER4 && boardState[31] == FELD_SPIELER4) {
                finished[aktiverSpieler] = true;
                spielStatistik.platzierungSetzen(anzahlFinished, namen[aktiverSpieler]);
                anzahlFinished++;
            }
        }
        if (anzahlFinished == spielerAnzahl - 1) {
            for (int i = 0; i < spielerAnzahl; i++) {
                if (!finished[i]) {
                    finished[i] = true;
                    spielStatistik.platzierungSetzen(anzahlFinished, namen[i]);
                }
            }
            anzahlFinished++;
        }
    }

    /**
     * @return [-1, -1] wenn keine Strafe, sonst Felder, die neu gezeichnet werden müssen
     */
    private int[] checkBestrafen(FeldBesetztStatus aktuellerSpieler, int from) {
        int[] ret = new int[]{-1, -1};
        System.out.println("check bestrafen");
        if (sechser) {
            // abrücken
            if (from != 48 && aktuellerSpieler == FELD_SPIELER1 && boardState[48] == FELD_SPIELER1 && boardState[48 + zahlGewuerfelt] != FELD_SPIELER1 && (boardState[0] == FELD_SPIELER1 || boardState[1] == FELD_SPIELER1 || boardState[2] == FELD_SPIELER1 || boardState[3] == FELD_SPIELER1)) {
                ret[0] = figurZurueckAufStartpositionen(48);
                ret[1] = 48;
            }
            if (from != 56 && aktuellerSpieler == FELD_SPIELER2 && boardState[56] == FELD_SPIELER2 && boardState[42 + zahlGewuerfelt] != FELD_SPIELER2 && (boardState[4] == FELD_SPIELER2 || boardState[5] == FELD_SPIELER2 || boardState[6] == FELD_SPIELER2 || boardState[7] == FELD_SPIELER2)) {
                ret[0] = figurZurueckAufStartpositionen(56);
                ret[1] = 56;
            }
            if (from != 64 && aktuellerSpieler == FELD_SPIELER3 && boardState[64] == FELD_SPIELER3 && boardState[52 + zahlGewuerfelt] != FELD_SPIELER3 && (boardState[8] == FELD_SPIELER3 || boardState[9] == FELD_SPIELER3 || boardState[10] == FELD_SPIELER3 || boardState[11] == FELD_SPIELER3)) {
                ret[0] = figurZurueckAufStartpositionen(64);
                ret[1] = 64;
            }
            if (from != 72 && aktuellerSpieler == FELD_SPIELER4 && boardState[72] == FELD_SPIELER4 && boardState[62 + zahlGewuerfelt] != FELD_SPIELER4 && (boardState[12] == FELD_SPIELER4 || boardState[13] == FELD_SPIELER4 || boardState[14] == FELD_SPIELER4 || boardState[15] == FELD_SPIELER4)) {
                ret[0] = figurZurueckAufStartpositionen(72);
                ret[1] = 72;
            }
            if (from != 80 && aktuellerSpieler == FELD_SPIELER5 && boardState[80] == FELD_SPIELER5 && boardState[62 + zahlGewuerfelt] != FELD_SPIELER5 && (boardState[16] == FELD_SPIELER5 || boardState[17] == FELD_SPIELER5 || boardState[18] == FELD_SPIELER5 || boardState[19] == FELD_SPIELER5)) {
                ret[0] = figurZurueckAufStartpositionen(80);
                ret[1] = 80;
            }
            if (from != 88 && aktuellerSpieler == FELD_SPIELER6 && boardState[88] == FELD_SPIELER6 && boardState[62 + zahlGewuerfelt] != FELD_SPIELER6 && (boardState[20] == FELD_SPIELER6 || boardState[21] == FELD_SPIELER6 || boardState[22] == FELD_SPIELER6 || boardState[23] == FELD_SPIELER6)) {
                ret[0] = figurZurueckAufStartpositionen(88);
                ret[1] = 88;
            }
            // schlagen
            if (from > 47 && ret[0] != -1) {
                for (int i = 47; i >= 0; i--) {
                    int intFieldTo = (i + zahlGewuerfelt) % 48 + 48;
                    if ((i + 48) != from && boardState[i + 48] == aktuellerSpieler && boardState[intFieldTo] != aktuellerSpieler && boardState[intFieldTo] != FELD_LEER) {
                        // nicht über Start beachten
                        if (aktuellerSpieler == FELD_SPIELER1 && (i + zahlGewuerfelt) >= 48) continue;
                        if (aktuellerSpieler == FELD_SPIELER2 && (i + zahlGewuerfelt) >= 8 && i < 8) continue;
                        if (aktuellerSpieler == FELD_SPIELER3 && (i + zahlGewuerfelt) >= 16 && i < 16) continue;
                        if (aktuellerSpieler == FELD_SPIELER4 && (i + zahlGewuerfelt) >= 24 && i < 24) continue;
                        if (aktuellerSpieler == FELD_SPIELER5 && (i + zahlGewuerfelt) >= 32 && i < 32) continue;
                        if (aktuellerSpieler == FELD_SPIELER6 && (i + zahlGewuerfelt) >= 40 && i < 40) continue;
                        ret[0] = figurZurueckAufStartpositionen(i + 48);
                        ret[1] = i + 48;
                    }
                }
            }
        } else {
            // abrücken
            if (from != 32 && aktuellerSpieler == FELD_SPIELER1 && boardState[32] == FELD_SPIELER1 && boardState[32 + zahlGewuerfelt] != FELD_SPIELER1 && (boardState[0] == FELD_SPIELER1 || boardState[1] == FELD_SPIELER1 || boardState[2] == FELD_SPIELER1 || boardState[3] == FELD_SPIELER1)) {
                ret[0] = figurZurueckAufStartpositionen(32);
                ret[1] = 32;
            }
            if (from != 42 && aktuellerSpieler == FELD_SPIELER2 && boardState[42] == FELD_SPIELER2 && boardState[42 + zahlGewuerfelt] != FELD_SPIELER2 && (boardState[4] == FELD_SPIELER2 || boardState[5] == FELD_SPIELER2 || boardState[6] == FELD_SPIELER2 || boardState[7] == FELD_SPIELER2)) {
                ret[0] = figurZurueckAufStartpositionen(42);
                ret[1] = 42;
            }
            if (from != 52 && aktuellerSpieler == FELD_SPIELER3 && boardState[52] == FELD_SPIELER3 && boardState[52 + zahlGewuerfelt] != FELD_SPIELER3 && (boardState[8] == FELD_SPIELER3 || boardState[9] == FELD_SPIELER3 || boardState[10] == FELD_SPIELER3 || boardState[11] == FELD_SPIELER3)) {
                ret[0] = figurZurueckAufStartpositionen(52);
                ret[1] = 52;
            }
            if (from != 62 && aktuellerSpieler == FELD_SPIELER4 && boardState[62] == FELD_SPIELER4 && boardState[62 + zahlGewuerfelt] != FELD_SPIELER4 && (boardState[12] == FELD_SPIELER4 || boardState[13] == FELD_SPIELER4 || boardState[14] == FELD_SPIELER4 || boardState[15] == FELD_SPIELER4)) {
                ret[0] = figurZurueckAufStartpositionen(62);
                ret[1] = 62;
            }
            // schlagen
            if (from > 31 && ret[0] != -1) {
                for (int i = 39; i >= 0; i--) {
                    int intFieldTo = (i + zahlGewuerfelt) % 40 + 32;
                    if ((i + 32) != from && boardState[i + 32] == aktuellerSpieler && boardState[intFieldTo] != aktuellerSpieler && boardState[intFieldTo] != FELD_LEER) {
                        // nicht über Start beachten
                        if (aktuellerSpieler == FELD_SPIELER1 && (i + zahlGewuerfelt) >= 40) continue;
                        if (aktuellerSpieler == FELD_SPIELER2 && (i + zahlGewuerfelt) >= 10 && i < 10) continue;
                        if (aktuellerSpieler == FELD_SPIELER3 && (i + zahlGewuerfelt) >= 20 && i < 20) continue;
                        if (aktuellerSpieler == FELD_SPIELER4 && (i + zahlGewuerfelt) >= 30 && i < 30) continue;
                        ret[0] = figurZurueckAufStartpositionen(i + 32);
                        ret[1] = i + 32;
                    }
                }
            }
        }
        return ret;
    }

    public synchronized WuerfelnRueckgabe throwDice(String sitzung) {
        int i = isInGame(sitzung);
        if (i == -1 || i != aktiverSpieler || zahlGewuerfelt < -1) return WuerfelnRueckgabe.NICHT_DRAN;
        return throwDiceIntern();
    }

    private synchronized WuerfelnRueckgabe throwDiceIntern() {
        if (aktiverSpieler == -1) return WuerfelnRueckgabe.KEIN_ZUG_MOEGLICH;

        timerWuerfeln.cancel();
        if (zahlGewuerfelt > 0) {
            if (getValidMove(boardState, felderVonSpieler[aktiverSpieler], zahlGewuerfelt, sechser)[0] != -1)
                return WuerfelnRueckgabe.FALSCHE_PHASE;
        }
        if (anzahlWuerfeln == 0) return WuerfelnRueckgabe.NICHT_DRAN;

        // todo entfernen
        if (wuerfelCount < wuerfel.length) {
            zahlGewuerfelt = wuerfel[wuerfelCount++];
        }else {
            zahlGewuerfelt = (int) (Math.random() * 6 + 1);
        }

        spielStatistik.incZahlGewuerfelt(aktiverSpieler, zahlGewuerfelt - 1);
        //System.out.println("throw diceIntern: " + namen[aktiverSpieler] + " : " + zahlGewuerfelt);
        anzahlWuerfeln--;
        displayDice(zahlGewuerfelt);
        if (zahlGewuerfelt == 6) {
            anzahlWuerfeln = 1;
        }
        int[] validMove = getValidMove(boardState, felderVonSpieler[aktiverSpieler], zahlGewuerfelt, sechser);
        System.out.println(Arrays.toString(validMove));
        if (validMove[0] == -1) { // kein Zug möglich
            nextMove(false);
            return WuerfelnRueckgabe.KEIN_ZUG_MOEGLICH;
        }
        if (clients[aktiverSpieler] != null) {
            timerZiehen = new Timer("TimerZiehen");
            timerZiehen.schedule(new ZiehenEnde(aktiverSpieler), DELAY_SPIELZUG);
            timerWaiting = new Timer("TimerWaiting");
            timerWaiting.schedule(new Waiting(aktiverSpieler), DELAY_WAITING);
        }
        return WuerfelnRueckgabe.ERFOLGREICH;
    }

    private void nextMove(boolean naechstenErzwingen) {
        zahlGewuerfelt = -5;
        new Thread(() -> {
            try {
                if (anzahlFinished == spielerAnzahl) {
                    beenden();
                    Thread.currentThread().interrupt();
                    return;
                }
                if (anzahlWuerfeln < 1 || naechstenErzwingen) {
                    //System.out.println("set next");
                    aktiverSpieler = (aktiverSpieler + 1) % spielerAnzahl;
                    while (finished[aktiverSpieler]) aktiverSpieler = (aktiverSpieler + 1) % spielerAnzahl;
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        System.err.println("schlafen unterbrochen");
                    }
                    aktivenSpielerSenden(aktiverSpieler);
                    displayDice(0);
                    anzahlWuerfeln = getAnzahlWuerfelnNext(aktiverSpieler);
                    //displayNewStateAll(boardState, new int[]{});
                }
                zahlGewuerfelt = -1;
                if (clients[aktiverSpieler] == null) {
                    doBotMove(aktiverSpieler, true);
                } else {
                    timerWuerfeln = new Timer("TimerWurf");
                    timerWuerfeln.schedule(new WuerfelnEnde(aktiverSpieler), DELAY_WUERFELN);
                }
            } catch (RuntimeException e) {
                System.err.println("beendet");
            }
        }).start();
    }

    private int getAnzahlWuerfelnNext(int spielerNext) {
        FeldBesetztStatus fieldState = felderVonSpieler[spielerNext];
        if (sechser) {
            if (fieldState == FELD_SPIELER1) {
                int countFehlen = 0;
                for (int i = 0; i < 4; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[27 - i] == FELD_LEER) return 1;
                return 3;
            }
            if (fieldState == FELD_SPIELER2) {
                int countFehlen = 0;
                for (int i = 4; i < 8; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[31 - i] == FELD_LEER) return 1;
                return 3;
            }
            if (fieldState == FELD_SPIELER3) {
                int countFehlen = 0;
                for (int i = 8; i < 12; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[35 - i] == FELD_LEER) return 1;
                return 3;
            }
            if (fieldState == FELD_SPIELER4) {
                int countFehlen = 0;
                for (int i = 12; i < 16; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[39 - i] == FELD_LEER) return 1;
                return 3;
            }
            if (fieldState == FELD_SPIELER5) {
                int countFehlen = 0;
                for (int i = 16; i < 20; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[43 - i] == FELD_LEER) return 1;
                return 3;
            }
            if (fieldState == FELD_SPIELER6) {
                int countFehlen = 0;
                for (int i = 20; i < 24; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[47 - i] == FELD_LEER) return 1;
                return 3;
            }
        } else {
            if (fieldState == FELD_SPIELER1) {
                int countFehlen = 0;
                for (int i = 0; i < 4; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[19 - i] == FELD_LEER) return 1;
                return 3;
            }
            if (fieldState == FELD_SPIELER2) {
                int countFehlen = 0;
                for (int i = 4; i < 8; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[23 - i] == FELD_LEER) return 1;
                return 3;
            }
            if (fieldState == FELD_SPIELER3) {
                int countFehlen = 0;
                for (int i = 8; i < 12; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[27 - i] == FELD_LEER) return 1;
                return 3;
            }
            if (fieldState == FELD_SPIELER4) {
                int countFehlen = 0;
                for (int i = 12; i < 16; i++) if (boardState[i] == FELD_LEER) countFehlen++;
                for (int i = 0; i < countFehlen; i++) if (boardState[31 - i] == FELD_LEER) return 1;
                return 3;
            }
        }
        System.err.println("Fehler oder ich bin doof");
        return -1;
    }

    private int figurZurueckAufStartpositionen(int feld) {
        FeldBesetztStatus field = boardState[feld];
        boardState[feld] = FELD_LEER;
        if (field == FELD_SPIELER1) {
            for (int i = 0; i < 4; i++) {
                if (boardState[i] == FELD_LEER) {
                    boardState[i] = FELD_SPIELER1;
                    return i;
                }
            }
        }
        if (field == FELD_SPIELER2) {
            for (int i = 4; i < 8; i++) {
                if (boardState[i] == FELD_LEER) {
                    boardState[i] = FELD_SPIELER2;
                    return i;
                }
            }
        }
        if (field == FELD_SPIELER3) {
            for (int i = 8; i < 12; i++) {
                if (boardState[i] == FELD_LEER) {
                    boardState[i] = FELD_SPIELER3;
                    return i;
                }
            }
        }
        if (field == FELD_SPIELER4) {
            for (int i = 12; i < 16; i++) {
                if (boardState[i] == FELD_LEER) {
                    boardState[i] = FELD_SPIELER4;
                    return i;
                }
            }
        }
        if (field == FELD_SPIELER5) {
            for (int i = 16; i < 20; i++) {
                if (boardState[i] == FELD_LEER) {
                    boardState[i] = FELD_SPIELER4;
                    return i;
                }
            }
        }
        if (field == FELD_SPIELER6) {
            for (int i = 20; i < 24; i++) {
                if (boardState[i] == FELD_LEER) {
                    boardState[i] = FELD_SPIELER4;
                    return i;
                }
            }
        }
        System.err.println("Fehler oder ich bin doof");
        return -1;
    }

    public Spielstatistik leaveGame(String sitzung) {
        return removePlayerIntern(sitzung);
    }

    private synchronized Spielstatistik removePlayerIntern(String sitzung) {
        int i = isInGame(sitzung);
        if (i == -1) return null;
        clients[i] = null;
        namen[i] = "Bot" + i;
        for (int j = 0; j < spielerAnzahl; j++) {
            if (clients[j] != null) break;
            if (j == spielerAnzahl - 1) {
                beenden();
                return spielStatistik.holeZumSenden();
            }
        }
        Spielstatistik spielStatistik1 = spielStatistik.holeZumSenden();
        spielStatistik.namenSetzen(namen);
        neueNamenSenden(namen);
        return spielStatistik1;
    }

    private void beenden() {
        System.err.println("beendet");
        aktiverSpieler = -1;
        zahlGewuerfelt = -1;
        anzahlWuerfeln = -1;
        spielLogikImpl.spielBeendet(this);
        spielVorbeiSenden();
    }

    private void doBotMove(int spieler, boolean sleep) {
        new Thread(() -> {
            if (zahlGewuerfelt < 1 && spieler == aktiverSpieler) { // schon gewürfelt, falls Spieler ersetzt / nicht gezogen
                try {
                    if (sleep) sleep(BOT_WAIT_WUERFELN);
                } catch (InterruptedException e) {
                    System.err.println("schlafen unterbrochen");
                }
                if (throwDiceIntern() == WuerfelnRueckgabe.KEIN_ZUG_MOEGLICH) return; // kein Zug möglich
            }
            int[] move = getValidMove(boardState, felderVonSpieler[aktiverSpieler], zahlGewuerfelt, sechser);
            if (move[0] != -1 && spieler == aktiverSpieler) {
                try {
                    if (sleep) sleep(BOT_WAIT_ZIEHEN);
                } catch (InterruptedException e) {
                    System.err.println("schlafen unterbrochen");
                }
                submitMoveIntern(move[0], move[1]);
            }
        }).start();
    }

    private void displayDice(int wurf) {
        new Thread(() -> anClient.wuerfelUpdaten(clients, wurf)).start();
    }

    private void displayNewState(FeldBesetztStatus[] state, int[] changed) {
        new Thread(() -> anClient.spielfeldUpdaten(clients, state, changed)).start();
    }

    private void neueNamenSenden(String[] namen) {
        new Thread(() -> anClient.spielNamenUpdaten(clients, namen)).start();
    }

    private void aktivenSpielerSenden(int aktiverSpieler) {
        new Thread(() -> anClient.aktuellenSpielerSetzen(clients, aktiverSpieler)).start();
    }

    private void spielVorbeiSenden() {
        Spielstatistik zumSenden = spielStatistik.holeZumSenden();
        new Thread(() -> {
            ArrayList<String> clientsSenden = new ArrayList<>();
            Pattern pattern = Pattern.compile(".*\\d$");
            for (String c : clients) {
                if (c != null) {
                    Matcher m = pattern.matcher(c);
                    if (!m.matches()) clientsSenden.add(c);
                }
            }
            anClient.spielVorbei(clientsSenden.toArray(new String[0]), zumSenden);
        }).start();
    }

    private int isInGame(String sitzung) {
        for (int i = 0; i < spielerAnzahl; i++) {
            if (clients[i].equals(sitzung)) return i;
        }
        return -1;
    }

    private class StartGame extends TimerTask {
        @Override
        public void run() {
            neueNamenSenden(namen);
            displayNewState(boardState, null);
            //aktiverSpieler = spielerAnzahl - 1;
            nextMove(true);
        }
    }

    private class WuerfelnEnde extends TimerTask {
        private final int spieler;

        public WuerfelnEnde(int spieler) {
            this.spieler = spieler;
        }

        @Override
        public void run() {
            if (zahlGewuerfelt < 1 && spieler == aktiverSpieler) {
                doBotMove(spieler, false);
                anClient.wuerfelnVorbei(clients[spieler]);
            }
        }
    }

    private class ZiehenEnde extends TimerTask {
        private final int spieler;

        public ZiehenEnde(int spieler) {
            this.spieler = spieler;
        }

        @Override
        public void run() {
            if (spieler == aktiverSpieler) {
                doBotMove(spieler, false);
                anClient.ziehenVorbei(clients[spieler]);
            }
        }
    }

    String[] getClients() {
        return clients;
    }

    private class Waiting extends TimerTask {
        private final int spieler;

        private Waiting(int spieler) {
            this.spieler = spieler;
        }

        @Override
        public void run() {
            if (spieler == aktiverSpieler) {
                anClient.gifAnzeigen(clients[spieler]);
            }
        }
    }
}
