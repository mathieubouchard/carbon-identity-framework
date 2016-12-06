/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.gateway.resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.framework.builder.IdentityRequestBuilder;
import org.wso2.carbon.identity.framework.exception.FrameworkClientException;
import org.wso2.msf4j.Request;

import java.util.Properties;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


public class MSF4JIdentityRequestFactory {

    private static Logger log = LoggerFactory.getLogger(MSF4JIdentityRequestFactory.class);
    protected Properties properties;

    public static final String TENANT_DOMAIN_PATTERN = "/t/([^/]+)";


    public String getName() {
        return "MSF4JIdentityRequestFactory";
    }

    public int getPriority() {
        return 100;
    }

    public boolean canHandle(Request request) {
        return true;
    }

    public IdentityRequestBuilder create(Request request) throws FrameworkClientException {

        IdentityRequestBuilder builder = new IdentityRequestBuilder();
        create(builder, request);
        return builder;
    }


    public ResponseBuilder handleException(FrameworkClientException exception, Request request) {
        return Response.status(400).entity(exception.getMessage());
    }


    public void create(IdentityRequestBuilder builder, Request request) throws FrameworkClientException {

        // get all headers
        request.getHeaders().getAll().forEach(header -> {
            builder.addHeader(header.getName(), header.getValue());
        });

        // get all properties
        request.getProperties().forEach(builder::addProperty);


        builder.setMethod(request.getHttpMethod());
        builder.setContentType(request.getContentType());
        builder.setRequestURI(request.getUri());

        // TODO : handle cookies


        // TODO: extract SP, tenant info, and others

        if (log.isDebugEnabled()) {
            log.debug("Identity Request is build from the inbound HTTP Request.");
        }
    }


}