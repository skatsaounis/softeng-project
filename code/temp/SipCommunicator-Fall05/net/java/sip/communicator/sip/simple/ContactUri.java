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
 *  The <contact> element contains a URL of the contact address.  It
 *  optionally has a 'priority' attribute, whose value means a relative
 *  priority of this contact address over the others.  The value of the
 *  attribute MUST be a decimal number between 0 and 1 inclusive with at
 *  most 3 digits after the decimal point.  Higher values indicate higher
 *  priority. Examples of priority values are 0, 0.021, 0.5, 1.00. If the
 *  'priority' attribute is omitted, applications MUST assign the contact
 *  address the lowest priority. If the 'priority' value is out of the
 *  range, applications just SHOULD ignore the value and process it as if
 *  the attribute was not present.
 *
 *  Applications SHOULD handle contacts with a higher priority as they
 *  have precedence over those with lower priorities. How they are
 *  actually treated is beyond this specification. Also, how to handle
 *  contacts with the same priority is up to implementations.
 *
 * @author Emil Ivov
 * @version 1.0
 */

public class ContactUri
    implements Comparable
{
    private float priority = 0;
    private String contactValue = null;

    /**
     * Sets the value of the priority attribute.
     * @param priority a three digit float value indicating contact priority
     */
    public void setPriority(float priority)
    {
        this.priority = priority;
    }

    /**
     * Sets the value of this contact.
     * @param contactValue the value to give to this contact
     */
    public void setContactValue(String contactValue)
    {
        this.contactValue = contactValue;
    }

    /**
     * Returns the value of the priority attribute.
     * @return a three digit float value indicating contact priority
     */
    public float getPriority()
    {
        return priority;
    }

    /**
     * Returns the value of this contact
     * @return the value of this contact
     */
    public String getContactValue()
    {
        return contactValue;
    }

    /**
     * Compares this object with the specified object for order.
     *
     * @param o the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     *   less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object o)
    {
        if(!(o instanceof ContactUri))
            return Integer.MAX_VALUE;
        return -(((int)(getPriority()*1000)) - (int)(((ContactUri)o).getPriority()*1000));
    }

    public Object clone()
    {
        ContactUri clone = new ContactUri();
        clone.setContactValue(new String(getContactValue()));
        clone.setPriority(getPriority());
        return clone;
    }
}
