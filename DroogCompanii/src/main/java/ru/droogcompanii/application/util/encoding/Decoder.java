package ru.droogcompanii.application.util.encoding;

/**
 * Created by ls on 21.02.14.
 */
public interface Decoder {
    String decode(byte[] bytes);
    String decode(String toDecode);
}
