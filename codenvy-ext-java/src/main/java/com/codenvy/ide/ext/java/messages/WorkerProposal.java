/*******************************************************************************
 * Copyright (c) 2012-2014 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.ide.ext.java.messages;

import com.google.gwt.webworker.client.messages.Message;
import com.google.gwt.webworker.client.messages.SerializationIndex;

/**
 * @author <a href="mailto:evidolob@codenvy.com">Evgen Vidolob</a>
 * @version $Id:
 */
public interface WorkerProposal extends Message {

    @SerializationIndex(1)
    String image();

    @SerializationIndex(2)
    String displayText();

    @SerializationIndex(3)
    boolean autoInsertable();

    @SerializationIndex(4)
    String id();
}
