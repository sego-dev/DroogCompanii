package ru.droogcompanii.application.util.encoding;

/**
 * Created by ls on 21.02.14.
 */
public class EncoderSHA1 extends BaseCompositeEncoder {

    protected EncoderSHA1(Encoder encoder) {
        super(encoder);
    }

    @Override
    protected String getAlgorithm() {
        return "SHA-1";
    }
}
