/**
 * 
 */
package io.pkts.packet.sip.impl;

import io.pkts.buffer.Buffer;
import io.pkts.buffer.Buffers;
import io.pkts.packet.sip.SipParseException;
import io.pkts.packet.sip.address.URI;
import io.pkts.packet.sip.address.impl.SipURIImpl;

import java.io.IOException;

/**
 * Class representing a sip request line
 * 
 * @author jonas@jonasborjesson.com
 */
public final class SipRequestLine extends SipInitialLine {

    private final Buffer method;
    private final Buffer requestUriBuffer;

    /**
     * The parsed request uri, which may be null if no one has asked about it
     * yet.
     */
    private URI requestURI;

    public SipRequestLine(final Buffer method, final Buffer requestUri) {
        super();
        assert method != null;
        assert requestUri != null;
        this.method = method;
        this.requestUriBuffer = requestUri;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestLine() {
        return true;
    }

    @Override
    public SipRequestLine toRequestLine() {
        return this;
    }

    public Buffer getMethod() {
        return this.method;
    }

    public URI getRequestUri() throws SipParseException {
        if (this.requestURI == null) {
            try {
                this.requestURI = SipURIImpl.frame(this.requestUriBuffer);
            } catch (final IOException e) {
                throw new SipParseException(0, "Unable to parse the request uri", e);
            }
        }
        return this.requestURI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Buffer getBuffer() {
        final Buffer tmp = Buffers.createBuffer(1024);
        getBytes(tmp);
        return tmp;
    }

    @Override
    public String toString() {
        return getBuffer().toString();
    }

    @Override
    public SipRequestLine clone() {
        final Buffer requestURI = this.requestUriBuffer.clone();
        return new SipRequestLine(this.method, requestURI);
    }

    @Override
    public void getBytes(final Buffer dst) {
        this.method.getBytes(0, dst);
        dst.write(SipParser.SP);
        if (this.requestURI != null) {
            this.requestURI.getBytes(dst);
        } else {
            this.requestUriBuffer.getBytes(0, dst);
        }
        dst.write(SipParser.SP);
        SipParser.SIP2_0.getBytes(0, dst);
    }
}
