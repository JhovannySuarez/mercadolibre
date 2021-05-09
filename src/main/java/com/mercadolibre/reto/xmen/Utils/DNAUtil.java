package com.mercadolibre.reto.xmen.Utils;

import java.util.Arrays;

public class DNAUtil {

    public static byte[] dnaToBytes(String[] dna) {
        return Arrays.toString(dna).getBytes();
    }
}
