package md.leonis.ystt.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.CRC32;

public class BinaryUtils {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        return new String(hexChars);
    }

    public static int crc32(byte[] bytes) {

        CRC32 crc = new CRC32();
        crc.update(bytes);
        return (int) crc.getValue();
    }

    public static int crc32(Path path) throws IOException {

        CRC32 crc = new CRC32();
        crc.update(IOUtils.loadBytes(path));
        return (int) crc.getValue();
    }

    public static byte[] md5(byte[] bytes) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sha1(byte[] bytes) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return md.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] unionArrays(int[] arr1, int[] arr2) {

        int[] merge = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                merge[k++] = arr1[i++];
            } else {
                merge[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {
            merge[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            merge[k++] = arr2[j++];
        }
        return removeDuplicates(Arrays.copyOfRange(merge, 0, k));
    }

    public static int[] intersectArrays(int[] arr1, int[] arr2) {

        int[] intersect = new int[Math.max(arr1.length, arr2.length)];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                i++;
            } else if (arr2[j] < arr1[i]) {
                j++;
            } else {
                intersect[k++] = arr1[i++];
            }
        }
        return Arrays.copyOfRange(intersect, 0, k);
    }

    /*@Measured
    static int[] intersectArrays0(int[] arr1, int[] arr2) {

        int[] c = new int[Math.max(arr1.length, arr2.length)];
        int k = 0;
        for (int n : arr2) {
            if (ArrayUtils.contains(arr1, n)) {
                c[k++] = n;
            }
        }

        return Arrays.copyOfRange(c, 0, k);
    }*/

    public static int[] filterArrays(int[] a, int index) {

        int[] c = new int[a.length];
        int k = 0;
        for (int n : a) {
            if (n % index == 0) {
                c[k++] = n;
            }
        }
        return Arrays.copyOfRange(c, 0, k);
    }

    static int[] removeDuplicates(int[] arr) { // Only for sorted arrays

        if (arr.length < 2) {
            return arr;
        }
        int j = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] != arr[i + 1]) {
                arr[j++] = arr[i];
            }
        }
        arr[j++] = arr[arr.length - 1];
        return Arrays.copyOfRange(arr, 0, j);
    }
}
