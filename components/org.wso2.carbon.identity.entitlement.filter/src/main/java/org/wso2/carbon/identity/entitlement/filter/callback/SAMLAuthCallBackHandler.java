/*
 * Copyright (c) 2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.entitlement.filter.callback;

import org.wso2.carbon.identity.entitlement.filter.exception.EntitlementFilterException;
import org.wso2.carbon.identity.entitlement.proxy.Attribute;
import org.wso2.carbon.identity.entitlement.proxy.ProxyConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import org.wso2.appserver.webapp.security.bean.*;
import com.google.gson.Gson;

//cprice - this class was created to support SAML session reading
public class SAMLAuthCallBackHandler extends EntitlementFilterCallBackHandler {
	
	/**
	 * We construct this in the event that SAML is configured and no custom 
	 * attributes are defined.
	 * @param request
	 * @throws EntitlementFilterException
	 */
    public SAMLAuthCallBackHandler(HttpServletRequest request) throws EntitlementFilterException {
        if ( request.getSession().getAttribute("LoggedInSession") != null ) {
        	Gson gson = new Gson();
            LoggedInSession loggedInSession = gson.fromJson(
            		request.getSession().getAttribute("LoggedInSession").toString(),
            		LoggedInSession.class);

            SAML2SSO saml = loggedInSession.getSAML2SSO();
            
            //cprice - set the subject ID
            setUserName(saml.getSubjectId());
        } else {
            throw new EntitlementFilterException("SAML session is null");
        }
    }
    
    /**
     * We construct this if we are using custom attributes derived from a SAML
     * assertion.
     * @param request
     * @param attributeList
     * @throws EntitlementFilterException
     */
    public SAMLAuthCallBackHandler(HttpServletRequest request, Map<String, String> attributeList) 
    		throws EntitlementFilterException {
    	if ( request.getSession().getAttribute("LoggedInSession") != null ) {
    		Gson gson = new Gson();
        	ArrayList<Attribute> list = new ArrayList<Attribute>();
        	Attribute[] returnAttr = null;
            LoggedInSession loggedInSession = gson.fromJson(request.getSession().getAttribute("LoggedInSession").toString(), LoggedInSession.class);

            Map<String, ArrayList> tempList = 
            		loggedInSession.getSAML2SSO().getSubjectAttributes();

            //cprice - iterate over attribute elements and find Federation ID and EmployeePositionName
            for (Map.Entry<String, ArrayList> pair : tempList.entrySet()) {
            	if (attributeList.containsKey(pair.getKey())){
	            	String listString = String.join(", ", pair.getValue());
	            	Attribute attr = new Attribute(
	            			attributeList.get(pair.getKey()), 
	            			pair.getKey(), 
	        				ProxyConstants.DEFAULT_DATA_TYPE,
	        				listString);
	        		list.add(attr);
            	}
            }
            
            returnAttr = list.toArray(new Attribute[list.size()]);
            setAttributes(returnAttr);
           
            //cprice - set the subject ID
            setUserName(loggedInSession.getSAML2SSO().getSubjectId());
        } else {
            throw new EntitlementFilterException("SAML session is null");
        }
    }
}
