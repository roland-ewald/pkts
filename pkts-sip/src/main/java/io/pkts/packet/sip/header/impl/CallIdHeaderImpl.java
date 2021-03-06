/**
 * 
 */
package io.pkts.packet.sip.header.impl;

import io.pkts.buffer.Buffer;
import io.pkts.packet.sip.SipParseException;
import io.pkts.packet.sip.header.CallIdHeader;

/**
 * @author jonas@jonasborjesson.com
 * 
 */
public final class CallIdHeaderImpl extends SipHeaderImpl implements CallIdHeader {

    public CallIdHeaderImpl(final Buffer value) {
        super(CallIdHeader.NAME, value);
    }

    public CallIdHeaderImpl(final boolean compactForm, final Buffer value) {
        super(compactForm ? CallIdHeader.COMPACT_NAME : CallIdHeader.NAME, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Buffer getCallId() {
        return getValue();
    }

    public static CallIdHeader frame(final Buffer buffer) throws SipParseException {
        return new CallIdHeaderImpl(buffer);
    }

    /**
     * 
     * @param compactForm
     * @param buffer
     * @return
     * @throws SipParseException
     */
    public static CallIdHeader frame(final boolean compactForm, final Buffer buffer) throws SipParseException {
        return new CallIdHeaderImpl(compactForm, buffer);
    }

    @Override
    public CallIdHeader clone() {
        try {
            return CallIdHeaderImpl.frame(getValue().clone());
        } catch (final SipParseException e) {
            throw new RuntimeException("Unable to clone the CallId-header", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final Buffer value = getValue();
        final int prime = 31;
        int result = 1;
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CallIdHeaderImpl other = (CallIdHeaderImpl) obj;
        final Buffer value = getValue();
        final Buffer otherValue = other.getValue();
        if (value == null) {
            if (otherValue != null) {
                return false;
            }
        } else if (!value.equals(otherValue)) {
            return false;
        }
        return true;
    }

}
