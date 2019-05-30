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
 *
 */

package org.wso2.carbon.identity.entitlement.filter.callback;

import org.wso2.carbon.identity.entitlement.proxy.Attribute;

public class EntitlementFilterCallBackHandler {

    private String userName;
    private String federationId;
    private String epn;
    private String empName;
    private Attribute[] attributes;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFederationId() {
        return federationId;
    }

    public void setFederationId(String federationId) {
        this.federationId = federationId;
    }
    
    public String getEpn() {
        return epn;
    }

    public void setEpn(String epn) {
        this.epn = epn;
    }
    
    public String getEmployerName() {
        return empName;
    }

    public void setEmployerName(String empName) {
        this.empName = empName;
    }

	public Attribute[] getAttributes() {
		return attributes;
	}

	public void setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
	}
}
