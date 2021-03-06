/*
 * Copyright (c) 2016 - 2017 Rui Zhao <renyuneyun@gmail.com>
 *
 * This file is part of Easer.
 *
 * Easer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Easer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Easer.  If not, see <http://www.gnu.org/licenses/>.
 */

package ryey.easer.plugins.operation.network_transmission;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

import ryey.easer.Utils;
import ryey.easer.commons.C;
import ryey.easer.commons.IllegalStorageDataException;
import ryey.easer.commons.plugindef.operationplugin.OperationData;

public class NetworkTransmissionOperationData implements OperationData {

    static final String K_PROTOCOL = "protocol";
    static final String K_REMOTE_ADDRESS = "remote_address";
    static final String K_REMOTE_PORT = "remote_port";
    static final String K_DATA = "data";

    TransmissionData data = null;

    public NetworkTransmissionOperationData() {
    }

    NetworkTransmissionOperationData(TransmissionData tdata) {
        data = tdata;
    }

    @Override
    public Object get() {
        return data;
    }

    @Override
    public void set(Object obj) {
        this.data = (TransmissionData) obj;
    }

    @Override
    public void parse(XmlPullParser parser, int version) throws IOException, XmlPullParserException, IllegalStorageDataException {
        throw new IllegalAccessError();
    }

    @Override
    public void serialize(XmlSerializer serializer) throws IOException {
        throw new IllegalAccessError();
    }

    @Override
    public void parse(String data, C.Format format, int version) throws IllegalStorageDataException {
        this.data = new TransmissionData();
        switch (format) {
            default:
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    this.data.protocol = TransmissionData.Protocol.valueOf(jsonObject.getString(K_PROTOCOL));
                    this.data.remote_address = jsonObject.getString(K_REMOTE_ADDRESS);
                    this.data.remote_port = jsonObject.getInt(K_REMOTE_PORT);
                    this.data.data = jsonObject.getString(K_DATA);
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new IllegalStorageDataException(e.getMessage());
                }
        }
    }

    @Override
    public String serialize(C.Format format) {
        String res = "";
        switch (format) {
            default:
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(K_PROTOCOL, data.protocol.toString());
                    jsonObject.put(K_REMOTE_ADDRESS, data.remote_address);
                    jsonObject.put(K_REMOTE_PORT, data.remote_port);
                    jsonObject.put(K_DATA, data.data);
                    res = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new IllegalStateException(e.getMessage());
                }
        }
        return res;
    }

    @Override
    public boolean isValid() {
        if (data == null)
            return false;
        if (data.protocol == null)
            return false;
        if (Utils.isBlank(data.remote_address))
            return false;
        if (data.remote_port == null)
            return false;
        if (data.remote_port <= 0)
            return false;
        return true;
    }
}
