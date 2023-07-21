package net.cheto97.rpgcraftmod.util;

public class IntToString {

    public static String formatearNumero(int numero) {
        String numeroString = String.valueOf(numero);
        int longitud = numeroString.length();
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            resultado.append(numeroString.charAt(i));

            if ((longitud - i) % 3 == 1 && i != longitud - 1) {
                resultado.append(".");
            }
        }

        return resultado.toString();
    }
}
