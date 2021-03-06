package security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.junit.Test;

public class SHA512Test {

    @Test
    public void test_hash_one_block_message() throws Exception {
        byte[] src = "abc".getBytes();
        byte[] actual = SHA512.hash(src);
        byte[] expected =
            bytes(
                0xddaf35a193617abaL,
                0xcc417349ae204131L,
                0x12e6fa4e89a97ea2L,
                0x0a9eeee64b55d39aL,
                0x2192992a274fc1a8L,
                0x36ba3c23a3feebbdL,
                0x454d4423643ce80eL,
                0x2a9ac94fa54ca49fL);
        assertThat(actual, is(expected));
    }

    @Test
    public void test_hash_multi_block_message() throws Exception {
        byte[] src =
            "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu"
                .getBytes();
        byte[] actual = SHA512.hash(src);
        byte[] expected =
            bytes(
                0x8e959b75dae313daL,
                0x8cf4f72814fc143fL,
                0x8f7779c6eb9f7fa1L,
                0x7299aeadb6889018L,
                0x501d289e4900f7e4L,
                0x331b99dec4b5433aL,
                0xc7d329eeb6dd2654L,
                0x5e96e55b874be909L);
        assertThat(actual, is(expected));
    }

    @Test
    public void test_hash_long_message() throws Exception {
        byte[] src = new byte[1_000_000];
        Arrays.fill(src, (byte) 'a');
        byte[] actual = SHA512.hash(src);
        byte[] expected =
            bytes(
                0xe718483d0ce76964L,
                0x4e2e42c7bc15b463L,
                0x8e1f98b13b204428L,
                0x5632a803afa973ebL,
                0xde0ff244877ea60aL,
                0x4cb0432ce577c31bL,
                0xeb009c5c2c49aa2eL,
                0x4eadb217ad8cc09bL);
        assertThat(actual, is(expected));
    }

    private static byte[] bytes(long... ls) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (long l : ls) {
            for (long j = 0L; j < 8L; j++) {
                out.write((int) ((l >>> (56L - j * 8L)) & 0xffL));
            }
        }
        return out.toByteArray();
    }
}
