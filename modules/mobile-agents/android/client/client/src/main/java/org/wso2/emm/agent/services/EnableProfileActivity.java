/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.emm.agent.services;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.wso2.emm.agent.ServerDetails;
import org.wso2.emm.agent.utils.ApplicationUtils;
import org.wso2.emm.agent.utils.Constants;
import org.wso2.emm.agent.utils.ProvisioningStateUtils;

/**
 * This activity is started after the provisioning is complete in {@link AgentDeviceAdminReceiver}.
 */
public class EnableProfileActivity extends Activity {

    DevicePolicyManager manager;
    ComponentName adminReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminReceiver = AgentDeviceAdminReceiver.getComponentName(this);
        if (savedInstanceState == null) {
            enableProfile();
            ApplicationUtils.disableGooglePlay(this);
            startEnrollment();
        }
    }

    private void enableProfile() {
        // This is the name for the newly created managed profile.
        manager.setProfileName(adminReceiver, Constants.TAG);
        // Enable the profile.
        manager.setProfileEnabled(adminReceiver);
    }

    private void startEnrollment(){
        Intent intent = new Intent(this, ServerDetails.class);
        startActivity(intent);
    }

}