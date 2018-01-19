/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js;

import java.util.Map;

/**
 * Javascript wrapper for Java level HashMap of HTTP headers/cookies.
 * This provides controlled access to HTTPServletRequest object's headers and cookies via provided javascript native
 * syntax.
 * Also it prevents writing an arbitrary values to the respective fields, keeping consistency on runtime.
 */
public class JsParameters extends AbstractJSObjectWrapper<Map> {

    private Map wrapped;

    public JsParameters(Map wrapped) {

        this.wrapped = wrapped;
    }

    @Override
    public Object getMember(String name) {

        if (wrapped == null) {
            return super.getMember(name);
        } else {
            return wrapped.get(name);
        }
    }

    @Override
    public boolean hasMember(String name) {

        if (wrapped == null) {
            return false;
        } else {
            return wrapped.get(name) != null;
        }
    }

    @Override
    public void removeMember(String name) {

        if (wrapped == null) {
            super.removeMember(name);
        } else {
            if (wrapped.containsKey(name)) {
                wrapped.remove(name);
            }
        }
    }

    @Override
    public void setMember(String name, Object value) {

        if (wrapped == null) {
            super.setMember(name, value);
            return;
        } else {
            wrapped.put(name, value);
        }
    }
}