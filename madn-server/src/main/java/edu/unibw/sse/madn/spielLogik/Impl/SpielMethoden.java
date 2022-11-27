package edu.unibw.sse.madn.spielLogik.Impl;

import edu.unibw.sse.madn.base.FeldBesetztStatus;

import java.util.Arrays;

import static edu.unibw.sse.madn.base.FeldBesetztStatus.*;

@SuppressWarnings("DuplicatedCode")
public class SpielMethoden {
    static FeldBesetztStatus[] feldFuellen(int spielerAnz, boolean sechser) {
        FeldBesetztStatus[] board = new FeldBesetztStatus[sechser?96:72];
        Arrays.fill(board, 0, sechser?96:72, FELD_LEER);
        if (spielerAnz == 2) {
            Arrays.fill(board, 0, 4, FELD_SPIELER1);
            Arrays.fill(board, 8, 12, FELD_SPIELER3);
        } else if(spielerAnz == 4) {
            Arrays.fill(board, 0, 4, FELD_SPIELER1);
            Arrays.fill(board, 4, 8, FELD_SPIELER2);
            Arrays.fill(board, 8, 12, FELD_SPIELER3);
            Arrays.fill(board, 12, 16, FELD_SPIELER4);
        } else {
            Arrays.fill(board, 0, 4, FELD_SPIELER1);
            Arrays.fill(board, 8, 12, FELD_SPIELER3);
            Arrays.fill(board, 16,20, FELD_SPIELER5);
            if (spielerAnz >= 5) {
                Arrays.fill(board, 4,8, FELD_SPIELER2);
                Arrays.fill(board, 12,16, FELD_SPIELER4);
            }
            if (spielerAnz == 6) Arrays.fill(board, 20,24, FELD_SPIELER6);
        }
        return board;
    }

    /**
     * überprüft, ob der Zug gesetzt werden durfte, unabhängig von einem eventuellen Prio Zug
     */
    public static boolean checkMoveValid(FeldBesetztStatus[] board, FeldBesetztStatus player, int from, int to, int dice, boolean sechser) {
        if (sechser) {
            return checkMoveValidInternSechser(board, player, from, to, dice);
        } else {
            return checkMoveValidInternVierer(board, player, from, to, dice);
        }
    }
    
    private static boolean checkMoveValidInternVierer(FeldBesetztStatus[] board, FeldBesetztStatus player, int from, int to, int dice) {
        // richtige Spielfigur/nicht selber schlagen
        if (board[from] != player || board[to] == player) return false;

        boolean b = ((from - 32 + dice) % 40 + 32) == to;

        // Spielzug auf Weg
        if (from >= 32 && to >= 32 && b && board[to] != player) {
            // nicht über Start beachten
            int i = from - 32;
            if (player == FELD_SPIELER1 && !((i + dice) >= 40)) return true;
            if (player == FELD_SPIELER2 && !((i + dice) >= 10 && i < 10)) return true;
            if (player == FELD_SPIELER3 && !((i + dice) >= 20 && i < 20)) return true;
            if (player == FELD_SPIELER4 && !((i + dice) >= 30 && i < 30)) return true;
        }
        // raus rücken
        if (dice == 6) {
            //noinspection ConstantConditions
            if (player == FELD_SPIELER1 && from >= 0 && from < 4 && to == 32 && board[to] != FELD_SPIELER1) return true;
            if (player == FELD_SPIELER2 && from >= 4 && from < 8 && to == 42 && board[to] != FELD_SPIELER2) return true;
            if (player == FELD_SPIELER3 && from >= 8 && from < 12 && to == 52 && board[to] != FELD_SPIELER3)
                return true;
            if (player == FELD_SPIELER4 && from >= 12 && from < 16 && to == 62 && board[to] != FELD_SPIELER4)
                return true;
        }
        // rein rücken
        if (player == FELD_SPIELER1 && from >= 66 && from <= 71 && to >= 16 && to <= 19 && from + dice == to + 56) {
            for (int i = 16; i < to; i++) if (board[i] == FELD_SPIELER1) return false;
            return true;
        }
        if (player == FELD_SPIELER2 && from >= 36 && from <= 41 && to >= 20 && to <= 23 && from + dice == to + 22) {
            for (int i = 20; i < to; i++) if (board[i] == FELD_SPIELER2) return false;
            return true;
        }
        if (player == FELD_SPIELER3 && from >= 46 && from <= 51 && to >= 24 && to <= 27 && from + dice == to + 28) {
            for (int i = 24; i < to; i++) if (board[i] == FELD_SPIELER3) return false;
            return true;
        }
        if (player == FELD_SPIELER4 && from >= 56 && from <= 61 && to >= 28 && to <= 31 && from + dice == to + 34) {
            for (int i = 28; i < to; i++) if (board[i] == FELD_SPIELER4) return false;
            return true;
        }
        // in Zielfeldern rücken
        if (player == FELD_SPIELER1 && from >= 16 && from <= 19 && to >= 17 && to <= 19 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER1) return false;
            return true;
        }
        if (player == FELD_SPIELER2 && from >= 20 && from <= 23 && to >= 21 && to <= 23 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER2) return false;
            return true;
        }
        if (player == FELD_SPIELER3 && from >= 24 && from <= 27 && to >= 25 && to <= 27 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER3) return false;
            return true;
        }
        if (player == FELD_SPIELER4 && from >= 28 && from <= 31 && to >= 29 && to <= 31 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER4) return false;
            return true;
        }
        return false;
    }

    private static boolean checkMoveValidInternSechser(FeldBesetztStatus[] board, FeldBesetztStatus player, int from, int to, int dice) {
        // richtige Spielfigur/nicht selber schlagen
        if (board[from] != player || board[to] == player) return false;

        boolean b = ((from - 48 + dice) % 48 + 48) == to;

        // Spielzug auf Weg
        if (from >= 48 && to >= 48 && b && board[to] != player) {
            // nicht über Start beachten
            int i = from - 48;
            if (!ueberStart(player, i, dice)) return true;
        }
        // raus rücken
        if (dice == 6) {
            //noinspection ConstantConditions
            if (player == FELD_SPIELER1 && from >= 0 && from < 4 && to == 48 && board[to] != FELD_SPIELER1) return true;
            if (player == FELD_SPIELER2 && from >= 4 && from < 8 && to == 56 && board[to] != FELD_SPIELER2) return true;
            if (player == FELD_SPIELER3 && from >= 8 && from < 12 && to == 64 && board[to] != FELD_SPIELER3) return true;
            if (player == FELD_SPIELER4 && from >= 12 && from < 16 && to == 72 && board[to] != FELD_SPIELER4) return true;
            if (player == FELD_SPIELER5 && from >= 16 && from < 20 && to == 80 && board[to] != FELD_SPIELER4) return true;
            if (player == FELD_SPIELER6 && from >= 20 && from < 24 && to == 88 && board[to] != FELD_SPIELER4) return true;
        }
        // rein rücken
        if (player == FELD_SPIELER1 && from >= 90 && from <= 95 && to >= 24 && to <= 28 && from + dice == to + 72) {
            for (int i = 24; i < to; i++) if (board[i] == FELD_SPIELER1) return false;
            return true;
        }
        if (player == FELD_SPIELER2 && from >= 50 && from <= 55 && to >= 28 && to <= 32 && from + dice == to + 28) {
            for (int i = 28; i < to; i++) if (board[i] == FELD_SPIELER2) return false;
            return true;
        }
        if (player == FELD_SPIELER3 && from >= 58 && from <= 63 && to >= 32 && to <= 36 && from + dice == to + 32) {
            for (int i = 32; i < to; i++) if (board[i] == FELD_SPIELER3) return false;
            return true;
        }
        if (player == FELD_SPIELER4 && from >= 66 && from <= 71 && to >= 36 && to <= 40 && from + dice == to + 36) {
            for (int i = 36; i < to; i++) if (board[i] == FELD_SPIELER4) return false;
            return true;
        }
        if (player == FELD_SPIELER5 && from >= 74 && from <= 79 && to >= 40 && to <= 44 && from + dice == to + 40) {
            for (int i = 40; i < to; i++) if (board[i] == FELD_SPIELER4) return false;
            return true;
        }
        if (player == FELD_SPIELER6 && from >= 82 && from <= 87 && to >= 44 && to <= 48 && from + dice == to + 44) {
            for (int i = 44; i < to; i++) if (board[i] == FELD_SPIELER4) return false;
            return true;
        }
        // in Zielfeldern rücken
        if (player == FELD_SPIELER1 && from >= 24 && from <= 27 && to >= 25 && to <= 27 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER1) return false;
            return true;
        }
        if (player == FELD_SPIELER2 && from >= 28 && from <= 31 && to >= 29 && to <= 31 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER2) return false;
            return true;
        }
        if (player == FELD_SPIELER3 && from >= 32 && from <= 35 && to >= 33 && to <= 35 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER3) return false;
            return true;
        }
        if (player == FELD_SPIELER4 && from >= 36 && from <= 39 && to >= 37 && to <= 39 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER4) return false;
            return true;
        }
        if (player == FELD_SPIELER5 && from >= 40 && from <= 43 && to >= 41 && to <= 43 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER4) return false;
            return true;
        }
        if (player == FELD_SPIELER6 && from >= 44 && from <= 47 && to >= 45 && to <= 47 && from + dice == to) {
            for (int i = from + 1; i < to; i++) if (board[i] == FELD_SPIELER4) return false;
            return true;
        }
        return false;
    }
    
    /**
     * @return null, falls kein Prio Move vorhanden, sonst [from, to]
     */
    public static int[] checkForPrioMove(FeldBesetztStatus[] board, FeldBesetztStatus player, int dice, boolean sechser) {
        if (sechser) {
            return checkForPrioMoveInternSechser(board, player, dice);
        } else {
            return checkForPrioMoveInternVierer(board, player, dice);
        }
    }
    
    private static int[] checkForPrioMoveInternVierer(FeldBesetztStatus[] board, FeldBesetztStatus player, int dice) {
        // rausrücken
        if (dice == 6) {
            if (player == FELD_SPIELER1) for (int i = 0; i < 4; i++)
                if (board[i] != FELD_LEER && board[32] != FELD_SPIELER1) return new int[]{i, 32};
            if (player == FELD_SPIELER2) for (int i = 4; i < 8; i++)
                if (board[i] != FELD_LEER && board[42] != FELD_SPIELER2) return new int[]{i, 42};
            if (player == FELD_SPIELER3) for (int i = 8; i < 12; i++)
                if (board[i] != FELD_LEER && board[52] != FELD_SPIELER3) return new int[]{i, 52};
            if (player == FELD_SPIELER4) for (int i = 12; i < 16; i++)
                if (board[i] != FELD_LEER && board[62] != FELD_SPIELER4) return new int[]{i, 62};
        }
        // abrücken
        if (player == FELD_SPIELER1 && board[32] == FELD_SPIELER1 && board[32 + dice] != FELD_SPIELER1 && (board[0] == FELD_SPIELER1 || board[1] == FELD_SPIELER1 || board[2] == FELD_SPIELER1 || board[3] == FELD_SPIELER1))
            return new int[]{32, 32 + dice};
        if (player == FELD_SPIELER2 && board[42] == FELD_SPIELER2 && board[42 + dice] != FELD_SPIELER2 && (board[4] == FELD_SPIELER2 || board[5] == FELD_SPIELER2 || board[6] == FELD_SPIELER2 || board[7] == FELD_SPIELER2))
            return new int[]{42, 42 + dice};
        if (player == FELD_SPIELER3 && board[52] == FELD_SPIELER3 && board[52 + dice] != FELD_SPIELER3 && (board[8] == FELD_SPIELER3 || board[9] == FELD_SPIELER3 || board[10] == FELD_SPIELER3 || board[11] == FELD_SPIELER3))
            return new int[]{52, 52 + dice};
        if (player == FELD_SPIELER4 && board[62] == FELD_SPIELER4 && board[62 + dice] != FELD_SPIELER4 && (board[12] == FELD_SPIELER4 || board[13] == FELD_SPIELER4 || board[14] == FELD_SPIELER4 || board[15] == FELD_SPIELER4))
            return new int[]{62, 62 + dice};
        // schlagen
        for (int i = 39; i >= 0; i--) {
            if (board[i + 32] == player && board[(i + dice) % 40 + 32] != player && board[(i + dice) % 40 + 32] != FELD_LEER) {
                // nicht über Start beachten
                if (player == FELD_SPIELER1 && (i + dice) >= 40) continue;
                if (player == FELD_SPIELER2 && (i + dice) >= 10 && i < 10) continue;
                if (player == FELD_SPIELER3 && (i + dice) >= 20 && i < 20) continue;
                if (player == FELD_SPIELER4 && (i + dice) >= 30 && i < 30) continue;
                return new int[]{i + 32, (i + dice) % 40 + 32};
            }
        }
        return null;
    }

    private static int[] checkForPrioMoveInternSechser(FeldBesetztStatus[] board, FeldBesetztStatus player, int dice) {
        // rausrücken
        if (dice == 6) {
            if (player == FELD_SPIELER1) for (int i = 0; i < 4; i++)
                if (board[i] != FELD_LEER && board[48] != FELD_SPIELER1) return new int[]{i, 48};
            if (player == FELD_SPIELER2) for (int i = 4; i < 8; i++)
                if (board[i] != FELD_LEER && board[56] != FELD_SPIELER2) return new int[]{i, 56};
            if (player == FELD_SPIELER3) for (int i = 8; i < 12; i++)
                if (board[i] != FELD_LEER && board[64] != FELD_SPIELER3) return new int[]{i, 64};
            if (player == FELD_SPIELER4) for (int i = 12; i < 16; i++)
                if (board[i] != FELD_LEER && board[72] != FELD_SPIELER4) return new int[]{i, 72};
            if (player == FELD_SPIELER5) for (int i = 16; i < 20; i++)
                if (board[i] != FELD_LEER && board[80] != FELD_SPIELER5) return new int[]{i, 80};
            if (player == FELD_SPIELER6) for (int i = 20; i < 24; i++)
                if (board[i] != FELD_LEER && board[88] != FELD_SPIELER6) return new int[]{i, 88};
        }
        // abrücken
        if (player == FELD_SPIELER1 && board[48] == FELD_SPIELER1 && board[48 + dice] != FELD_SPIELER1 && (board[0] == FELD_SPIELER1 || board[1] == FELD_SPIELER1 || board[2] == FELD_SPIELER1 || board[3] == FELD_SPIELER1))
            return new int[]{48, 48 + dice};
        if (player == FELD_SPIELER2 && board[56] == FELD_SPIELER2 && board[56 + dice] != FELD_SPIELER2 && (board[4] == FELD_SPIELER2 || board[5] == FELD_SPIELER2 || board[6] == FELD_SPIELER2 || board[7] == FELD_SPIELER2))
            return new int[]{56, 56 + dice};
        if (player == FELD_SPIELER3 && board[64] == FELD_SPIELER3 && board[64 + dice] != FELD_SPIELER3 && (board[8] == FELD_SPIELER3 || board[9] == FELD_SPIELER3 || board[10] == FELD_SPIELER3 || board[11] == FELD_SPIELER3))
            return new int[]{64, 64 + dice};
        if (player == FELD_SPIELER4 && board[72] == FELD_SPIELER4 && board[72 + dice] != FELD_SPIELER4 && (board[12] == FELD_SPIELER4 || board[13] == FELD_SPIELER4 || board[14] == FELD_SPIELER4 || board[15] == FELD_SPIELER4))
            return new int[]{72, 72 + dice};
        if (player == FELD_SPIELER5 && board[80] == FELD_SPIELER5 && board[80 + dice] != FELD_SPIELER5 && (board[16] == FELD_SPIELER5 || board[17] == FELD_SPIELER5 || board[18] == FELD_SPIELER5 || board[19] == FELD_SPIELER5))
            return new int[]{80, 80 + dice};
        if (player == FELD_SPIELER6 && board[88] == FELD_SPIELER6 && board[88 + dice] != FELD_SPIELER6 && (board[20] == FELD_SPIELER6 || board[21] == FELD_SPIELER6 || board[22] == FELD_SPIELER6 || board[23] == FELD_SPIELER6))
            return new int[]{88, 88 + dice};
        // schlagen
        for (int i = 47; i >= 0; i--) {
            int fieldTo = (i + dice) % 48 + 48;
            if (board[i + 48] == player && board[fieldTo] != player && board[fieldTo] != FELD_LEER) {
                // nicht über Start beachten
                if (ueberStart(player, i ,dice)) continue;
                return new int[]{i + 48, fieldTo};
            }
        }
        return null;
    }
    
    /**
     * @return [-1, -1] falls kein Zug möglich, sonst [from, to] berücksichtigt Prio
     */
    public static int[] getValidMove(FeldBesetztStatus[] boardState, FeldBesetztStatus field, int wurf, boolean sechser) {
        if (sechser) {
            return getValidMoveInternSechser(boardState, field, wurf);
        } else {
            return getValidMoveInternVierer(boardState, field, wurf);
        }
    }

    private static int[] getValidMoveInternVierer(FeldBesetztStatus[] boardState, FeldBesetztStatus field, int wurf) {
        int[] prio = checkForPrioMoveInternVierer(boardState, field, wurf);
        if (prio != null) return prio;
        // innerhalb Zielfelder
        if (field == FELD_SPIELER1) {
            if (wurf < 4 && checkMoveValidInternVierer(boardState, field, 16, 16 + wurf, wurf)) return new int[]{16, 16 + wurf};
            if (wurf < 3 && checkMoveValidInternVierer(boardState, field, 17, 17 + wurf, wurf)) return new int[]{17, 17 + wurf};
            if (wurf < 2 && checkMoveValidInternVierer(boardState, field, 18, 18 + wurf, wurf)) return new int[]{18, 18 + wurf};
        }
        if (field == FELD_SPIELER2) {
            if (wurf < 4 && checkMoveValidInternVierer(boardState, field, 20, 20 + wurf, wurf)) return new int[]{20, 20 + wurf};
            if (wurf < 3 && checkMoveValidInternVierer(boardState, field, 21, 21 + wurf, wurf)) return new int[]{21, 21 + wurf};
            if (wurf < 2 && checkMoveValidInternVierer(boardState, field, 22, 22 + wurf, wurf)) return new int[]{22, 22 + wurf};
        }
        if (field == FELD_SPIELER3) {
            if (wurf < 4 && checkMoveValidInternVierer(boardState, field, 24, 24 + wurf, wurf)) return new int[]{24, 24 + wurf};
            if (wurf < 3 && checkMoveValidInternVierer(boardState, field, 25, 25 + wurf, wurf)) return new int[]{25, 25 + wurf};
            if (wurf < 2 && checkMoveValidInternVierer(boardState, field, 26, 26 + wurf, wurf)) return new int[]{26, 26 + wurf};
        }
        if (field == FELD_SPIELER4) {
            if (wurf < 4 && checkMoveValidInternVierer(boardState, field, 28, 28 + wurf, wurf)) return new int[]{28, 28 + wurf};
            if (wurf < 3 && checkMoveValidInternVierer(boardState, field, 29, 29 + wurf, wurf)) return new int[]{29, 29 + wurf};
            if (wurf < 2 && checkMoveValidInternVierer(boardState, field, 30, 30 + wurf, wurf)) return new int[]{30, 30 + wurf};
        }
        // in Zielfelder
        if (field == FELD_SPIELER1) for (int i = 71; i > 71 - wurf; i--)
            if (boardState[i] == FELD_SPIELER1 && i + wurf < 76)
                if (checkMoveValidInternVierer(boardState, field, i, i + wurf - 56, wurf)) return new int[]{i, i + wurf - 56};
        if (field == FELD_SPIELER2) for (int i = 41; i > 41 - wurf; i--)
            if (boardState[i] == FELD_SPIELER2 && i + wurf < 46)
                if (checkMoveValidInternVierer(boardState, field, i, i + wurf - 22, wurf)) return new int[]{i, i + wurf - 22};
        if (field == FELD_SPIELER3) for (int i = 51; i > 51 - wurf; i--)
            if (boardState[i] == FELD_SPIELER3 && i + wurf < 56)
                if (checkMoveValidInternVierer(boardState, field, i, i + wurf - 28, wurf)) return new int[]{i, i + wurf - 28};
        if (field == FELD_SPIELER4) for (int i = 61; i > 61 - wurf; i--)
            if (boardState[i] == FELD_SPIELER4 && i + wurf < 66)
                if (checkMoveValidInternVierer(boardState, field, i, i + wurf - 34, wurf)) return new int[]{i, i + wurf - 34};
        // sonst zufällig
        int[] start = new int[4];
        int count = 0;
        for (int i = 32; i < 72; i++) {
            if (boardState[i] == field) {
                if (checkMoveValidInternVierer(boardState, field, i, (i - 32 + wurf) % 40 + 32, wurf)) start[count++] = i;
            }
        }
        if (count > 0) {
            int auswahl = start[(int) (Math.random() * count)];
            return new int[]{auswahl, (auswahl - 32 + wurf) % 40 + 32};
        }

        return new int[]{-1, -1};
    }

    private static int[] getValidMoveInternSechser(FeldBesetztStatus[] boardState, FeldBesetztStatus field, int wurf) {
        int[] prio = checkForPrioMoveInternSechser(boardState, field, wurf);
        if (prio != null) return prio;
        // innerhalb Zielfelder
        if (field == FELD_SPIELER1) {
            if (wurf < 4 && checkMoveValidInternSechser(boardState, field, 24, 24 + wurf, wurf)) return new int[]{24, 24 + wurf};
            if (wurf < 3 && checkMoveValidInternSechser(boardState, field, 25, 25 + wurf, wurf)) return new int[]{25, 25 + wurf};
            if (wurf < 2 && checkMoveValidInternSechser(boardState, field, 26, 26 + wurf, wurf)) return new int[]{26, 26 + wurf};
        }
        if (field == FELD_SPIELER2) {
            if (wurf < 4 && checkMoveValidInternSechser(boardState, field, 28, 28 + wurf, wurf)) return new int[]{28, 28 + wurf};
            if (wurf < 3 && checkMoveValidInternSechser(boardState, field, 29, 29 + wurf, wurf)) return new int[]{29, 29 + wurf};
            if (wurf < 2 && checkMoveValidInternSechser(boardState, field, 30, 30 + wurf, wurf)) return new int[]{30, 30 + wurf};
        }
        if (field == FELD_SPIELER3) {
            if (wurf < 4 && checkMoveValidInternSechser(boardState, field, 32, 32 + wurf, wurf)) return new int[]{32, 32 + wurf};
            if (wurf < 3 && checkMoveValidInternSechser(boardState, field, 33, 33 + wurf, wurf)) return new int[]{33, 33 + wurf};
            if (wurf < 2 && checkMoveValidInternSechser(boardState, field, 34, 34 + wurf, wurf)) return new int[]{34, 34 + wurf};
        }
        if (field == FELD_SPIELER4) {
            if (wurf < 4 && checkMoveValidInternSechser(boardState, field, 36, 36 + wurf, wurf)) return new int[]{36, 36 + wurf};
            if (wurf < 3 && checkMoveValidInternSechser(boardState, field, 37, 37 + wurf, wurf)) return new int[]{37, 37 + wurf};
            if (wurf < 2 && checkMoveValidInternSechser(boardState, field, 38, 38 + wurf, wurf)) return new int[]{38, 38 + wurf};
        }
        if (field == FELD_SPIELER5) {
            if (wurf < 4 && checkMoveValidInternSechser(boardState, field, 40, 40 + wurf, wurf)) return new int[]{40, 40 + wurf};
            if (wurf < 3 && checkMoveValidInternSechser(boardState, field, 41, 41 + wurf, wurf)) return new int[]{41, 41 + wurf};
            if (wurf < 2 && checkMoveValidInternSechser(boardState, field, 42, 42 + wurf, wurf)) return new int[]{42, 42 + wurf};
        }
        if (field == FELD_SPIELER6) {
            if (wurf < 4 && checkMoveValidInternSechser(boardState, field, 43, 43 + wurf, wurf)) return new int[]{43, 43 + wurf};
            if (wurf < 3 && checkMoveValidInternSechser(boardState, field, 44, 44 + wurf, wurf)) return new int[]{44, 44 + wurf};
            if (wurf < 2 && checkMoveValidInternSechser(boardState, field, 45, 45 + wurf, wurf)) return new int[]{45, 45 + wurf};
        }
        // in Zielfelder
        if (field == FELD_SPIELER1) for (int i = 95; i > 95 - wurf; i--)
            if (boardState[i] == FELD_SPIELER1 && i + wurf < 100)
                if (checkMoveValidInternSechser(boardState, field, i, i + wurf - 72, wurf)) return new int[]{i, i + wurf - 72};
        if (field == FELD_SPIELER2) for (int i = 55; i > 55 - wurf; i--)
            if (boardState[i] == FELD_SPIELER2 && i + wurf < 60)
                if (checkMoveValidInternSechser(boardState, field, i, i + wurf - 28, wurf)) return new int[]{i, i + wurf - 28};
        if (field == FELD_SPIELER3) for (int i = 63; i > 63 - wurf; i--)
            if (boardState[i] == FELD_SPIELER3 && i + wurf < 68)
                if (checkMoveValidInternSechser(boardState, field, i, i + wurf - 32, wurf)) return new int[]{i, i + wurf - 32};
        if (field == FELD_SPIELER4) for (int i = 71; i > 71 - wurf; i--)
            if (boardState[i] == FELD_SPIELER4 && i + wurf < 76)
                if (checkMoveValidInternSechser(boardState, field, i, i + wurf - 36, wurf)) return new int[]{i, i + wurf - 36};
        if (field == FELD_SPIELER5) for (int i = 79; i > 79 - wurf; i--)
            if (boardState[i] == FELD_SPIELER5 && i + wurf < 84)
                if (checkMoveValidInternSechser(boardState, field, i, i + wurf - 40, wurf)) return new int[]{i, i + wurf - 40};
        if (field == FELD_SPIELER6) for (int i = 87; i > 87 - wurf; i--)
            if (boardState[i] == FELD_SPIELER6 && i + wurf < 92)
                if (checkMoveValidInternSechser(boardState, field, i, i + wurf - 44, wurf)) return new int[]{i, i + wurf - 44};
        // sonst zufällig
        int[] start = new int[4];
        int count = 0;
        for (int i = 48; i < 96; i++) {
            if (boardState[i] == field) {
                if (checkMoveValidInternSechser(boardState, field, i, (i - 48 + wurf) % 48 + 48, wurf)) start[count++] = i;
            }
        }
        if (count > 0) {
            int auswahl = start[(int) (Math.random() * count)];
            return new int[]{auswahl, (auswahl - 48 + wurf) % 48 + 48};
        }

        return new int[]{-1, -1};
    }

    @SuppressWarnings("RedundantIfStatement")
    static boolean ueberStart(FeldBesetztStatus spieler, int i, int dice) {
        if (spieler == FELD_SPIELER1 && (i + dice) >= 48) return true;
        if (spieler == FELD_SPIELER2 && (i + dice) >= 8 && i < 8) return true;
        if (spieler == FELD_SPIELER3 && (i + dice) >= 16 && i < 16) return true;
        if (spieler == FELD_SPIELER4 && (i + dice) >= 24 && i < 24) return true;
        if (spieler == FELD_SPIELER5 && (i + dice) >= 32 && i < 32) return true;
        if (spieler == FELD_SPIELER6 && (i + dice) >= 40 && i < 40) return true;
        return false;
    }
}
