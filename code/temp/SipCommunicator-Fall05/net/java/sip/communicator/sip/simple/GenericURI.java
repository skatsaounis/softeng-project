/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2000 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * Portions of this software are based upon public domain software
 * originally written at the National Center for Supercomputing Applications,
 * University of Illinois, Urbana-Champaign.
 */
package net.java.sip.communicator.sip.simple;



/**
 * The class is used to store different kinds of URI instances.
 * @author Emil Ivov
 * @version 1.0
 */

public class GenericURI
{
    public static final String SIP_SCHEME      = "sip";
    public static final String SIPS_SCHEME     = "sips";
    public static final String PRESENCE_SCHEME = "pres";
    public static final String MAIL_SCHEME     = "mailto";
    public static final String TEL_SCHEME      = "tel";

    /**
     * The scheme of this uri.
     */
    private String scheme = SIP_SCHEME;


    /**
     * only valid when scheme is SIP_SCHEME
     */
    private int port = 5060;

    /**
     * The address portiong of the Uri
     */
    private String address = null;

    /**
     * Any parameters found after the address part of the URI
     */
    private String uriParams = null;

    public GenericURI()
    {

    }

    /**
     * Sets the scheme of the URI.
     * @param scheme the scheme of the URI.
     */
    public void setScheme(String scheme)
    {
        this.scheme = scheme;
    }

    /**
     * Returns the scheme of the URI.
     * @param scheme the scheme of the URI.
     */
    public String getScheme()
    {
        return scheme;
    }

    /**
     * Sets the port of the URI. The port is only used with sip and pres schemes
     * and is ignored otherwise.
     * @param port the port part of the uri.
     */
    public void setPort(int port)
    {
        this.port = port;
    }

    /**
     * Returns the port used by the URI. This method would always return a result
     * even if the port field is not valid in a given context (e.g. tel scheme)
     * @return the port used by the URI
     */
    public int getPort()
    {
        return port;
    }

    /**
     * Creates a GenericURI object after parsing the specified string.
     * @param uriStr the uri string
     * @throws IllegalArgumentException if <code>uriStr</code> does not contain
     * a valid URI
     * @return the newly constructed URI.
     */
    public static GenericURI parseURI(String uriStr)
         throws IllegalArgumentException
    {
        if(uriStr == null
           || uriStr.length() == 0)
            throw new IllegalArgumentException("The URI String must not be null or with 0 length");
        GenericURI uri = new GenericURI();

        int colonIndex   = uriStr.indexOf(':');
        int bracketIndex = uriStr.indexOf('[');

        if(colonIndex == -1
           || ( bracketIndex > -1 && colonIndex > bracketIndex))
            throw new IllegalArgumentException("No URI scheme found in the following uri: " + uriStr);

        uri.setScheme(uriStr.substring(0, colonIndex));

        int portColonIndex = -1;

        //move colon index forward in case we are dealing with an IPv6 address.
        //if(uriStr.charAt(colonIndex + 1) == '[') - bug fix by Ling, Fang-Yu
        if(uriStr.indexOf('[') != -1)
            portColonIndex = uriStr.indexOf(':', uriStr.indexOf(']'));
        else
            portColonIndex = uriStr.indexOf(':', colonIndex + 1);

        int semiColonIndex = uriStr.indexOf(';', colonIndex + 1);

        semiColonIndex = ( (semiColonIndex == -1)? uriStr.length() : semiColonIndex );

        //get the port
        if(portColonIndex != -1)
        {
            try {
                uri.port = Integer.parseInt(uriStr.substring(portColonIndex + 1,
                    semiColonIndex));
            }
            catch (NumberFormatException ex) {
                throw new IllegalArgumentException(
                    "Failed to parse the port part of the following uri: "
                        + uriStr);
            }
        }
        else
            portColonIndex = semiColonIndex;

        //get the uri value
        uri.address = uriStr.substring(colonIndex + 1, portColonIndex);

        if(semiColonIndex < uriStr.length())
            uri.uriParams = uriStr.substring(semiColonIndex + 1);

        return uri;
    }

    /**
     * Sets the address part (and only the address part) of this URI.
     */
    public void setAddressPart(String addressPart)
    {
        addressPart = addressPart.trim();
        if(addressPart.indexOf(':') != -1
           && addressPart.charAt(0) != '[')
            //we have an IPv6 address
            this.address = "[" + addressPart + "]";
        else
            this.address = addressPart;

    }


    /**
     * Returns the address part of this URI.
     * @return String
     */
    public String getAddressPart()
    {
        return address;
    }

    /**
     * Returns all parameters of this uri.
     * @return a String containing all parameters of this uri.
     */
    public String getUriParams()
    {
        return this.uriParams;
    }

    /**
     * Returns the String representation of this URI.
     * @return the String representation of this URI.
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(getScheme());

        buffer.append(':').append(getAddressPart());

        if(getPort() != 5060)
            buffer.append(':').append(String.valueOf(getPort()));

        if(getUriParams() != null
           && getUriParams().length() > 0)
            buffer.append(';').append(getUriParams());

        return buffer.toString();
    }

    /**
     * Creates and returns a copy of this uri.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not support
     *   the <code>Cloneable</code> interface. Subclasses that override the
     *   <code>clone</code> method can also throw this exception to indicate
     *   that an instance cannot be cloned.
     * @todo Implement this java.lang.Object method
     */
    protected Object clone()
    {
        GenericURI clone = new GenericURI();

        clone.setScheme(new String(getScheme()));
        clone.setPort(getPort());
        clone.setAddressPart(new String(getAddressPart()));
        clone.setUriParams(getUriParams() == null? null:new String(getUriParams()));

        return clone;
    }

    /**
     * Sets an parameter string for this URI.
     *
     * @param paramString a string of parameters for this uri.
     */
    private void setUriParams(String paramString)
    {
        this.uriParams = paramString;
    }

    /**
     * Compares this GenericURI with <code>uri</code> ignoring all uri parameters
     * @param uri the uri to match this instance against.
     * @return true if uri represents the same entity as this instance (port,
     * address and scheme fields are equal) and false otherwise.
     */
    public boolean matches(GenericURI uri)
    {
        return (   uri.getScheme().equals(getScheme())
                && uri.getAddressPart().equals(getAddressPart())
                && uri.getPort() == getPort());
    }

}
