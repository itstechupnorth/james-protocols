/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/


package org.apache.james.protocols.pop3.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.james.protocols.api.CommandHandler;
import org.apache.james.protocols.api.ExtensibleHandler;
import org.apache.james.protocols.api.Request;
import org.apache.james.protocols.api.Response;
import org.apache.james.protocols.api.WiringException;
import org.apache.james.protocols.pop3.POP3Response;
import org.apache.james.protocols.pop3.POP3Session;

/**
 * This handler is used to handle CAPA commands
 *
 */
public class CapaCmdHandler implements CommandHandler<POP3Session>, ExtensibleHandler, CapaCapability{
	public final static String COMMAND_NAME = "CAPA";
	private List<CapaCapability> caps;





	/*
	 * (non-Javadoc)
	 * @see org.apache.james.protocols.api.CommandHandler#onCommand(org.apache.james.protocols.api.ProtocolSession, org.apache.james.protocols.api.Request)
	 */
    public Response onCommand(POP3Session session, Request request) {
	    POP3Response response = new POP3Response(POP3Response.OK_RESPONSE,"Capability list follows");
		
		for (int i = 0; i < caps.size(); i++) {
			List<String> cList = caps.get(i).getImplementedCapabilities(session);
			for (int a = 0; a < cList.size(); a++) {
				response.appendLine(cList.get(a));
			}
		}
		response.appendLine(".");
		return response;
	}
	


    /*
     * (non-Javadoc)
     * @see org.apache.james.protocols.api.ExtensibleHandler#getMarkerInterfaces()
     */
	@SuppressWarnings("unchecked")
    public List<Class<?>> getMarkerInterfaces() {
        List<Class<?>> mList = new ArrayList();
        mList.add(CapaCapability.class);
        return mList;
    }

	/*
	 * (non-Javadoc)
	 * @see org.apache.james.protocols.api.ExtensibleHandler#wireExtensions(java.lang.Class, java.util.List)
	 */
    @SuppressWarnings("unchecked")
    public void wireExtensions(Class interfaceName, List extension)
            throws WiringException {
        if (interfaceName.equals(CapaCapability.class)) {
            caps = extension;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.apache.james.protocols.api.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
        List<String> commands = new ArrayList<String>();
        commands.add(COMMAND_NAME);
        return commands;
    }


    /*
     * (non-Javadoc)
     * @see org.apache.james.protocols.pop3.core.CapaCapability#getImplementedCapabilities(org.apache.james.protocols.pop3.POP3Session)
     */
	public List<String> getImplementedCapabilities(POP3Session session) {
		 List<String> cList = new ArrayList<String>();
	     cList.add("PIPELINING");
	     return cList;
	}

}