package com.orciuch.telematics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackerEnvelope {

    private Position position;
    private Device device;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    // ----------------- Position -----------------

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Position {
        private Long id;
        private Map<String, Object> attributes;   // sat, rssi, batteryLevel, steps, etc.
        private Long deviceId;
        private String protocol;
        private String serverTime;
        private String deviceTime;
        private String fixTime;
        private Boolean valid;
        private Double latitude;
        private Double longitude;
        private Double altitude;
        private Double speed;
        private Double course;
        private String address;
        private Double accuracy;
        private Network network;
        private List<Long> geofenceIds;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        public Long getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Long deviceId) {
            this.deviceId = deviceId;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getServerTime() {
            return serverTime;
        }

        public void setServerTime(String serverTime) {
            this.serverTime = serverTime;
        }

        public String getDeviceTime() {
            return deviceTime;
        }

        public void setDeviceTime(String deviceTime) {
            this.deviceTime = deviceTime;
        }

        public String getFixTime() {
            return fixTime;
        }

        public void setFixTime(String fixTime) {
            this.fixTime = fixTime;
        }

        public Boolean getValid() {
            return valid;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getAltitude() {
            return altitude;
        }

        public void setAltitude(Double altitude) {
            this.altitude = altitude;
        }

        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }

        public Double getCourse() {
            return course;
        }

        public void setCourse(Double course) {
            this.course = course;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Double getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(Double accuracy) {
            this.accuracy = accuracy;
        }

        public Network getNetwork() {
            return network;
        }

        public void setNetwork(Network network) {
            this.network = network;
        }

        public List<Long> getGeofenceIds() {
            return geofenceIds;
        }

        public void setGeofenceIds(List<Long> geofenceIds) {
            this.geofenceIds = geofenceIds;
        }
    }

    // ----------------- Network -----------------

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Network {
        private String radioType;
        private Boolean considerIp;
        private List<CellTower> cellTowers;

        public String getRadioType() {
            return radioType;
        }

        public void setRadioType(String radioType) {
            this.radioType = radioType;
        }

        public Boolean getConsiderIp() {
            return considerIp;
        }

        public void setConsiderIp(Boolean considerIp) {
            this.considerIp = considerIp;
        }

        public List<CellTower> getCellTowers() {
            return cellTowers;
        }

        public void setCellTowers(List<CellTower> cellTowers) {
            this.cellTowers = cellTowers;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CellTower {
        private Integer cellId;
        private Integer locationAreaCode;
        private Integer mobileCountryCode;
        private Integer mobileNetworkCode;
        private Integer signalStrength;

        public Integer getCellId() {
            return cellId;
        }

        public void setCellId(Integer cellId) {
            this.cellId = cellId;
        }

        public Integer getLocationAreaCode() {
            return locationAreaCode;
        }

        public void setLocationAreaCode(Integer locationAreaCode) {
            this.locationAreaCode = locationAreaCode;
        }

        public Integer getMobileCountryCode() {
            return mobileCountryCode;
        }

        public void setMobileCountryCode(Integer mobileCountryCode) {
            this.mobileCountryCode = mobileCountryCode;
        }

        public Integer getMobileNetworkCode() {
            return mobileNetworkCode;
        }

        public void setMobileNetworkCode(Integer mobileNetworkCode) {
            this.mobileNetworkCode = mobileNetworkCode;
        }

        public Integer getSignalStrength() {
            return signalStrength;
        }

        public void setSignalStrength(Integer signalStrength) {
            this.signalStrength = signalStrength;
        }
    }

    // ----------------- Device -----------------

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {
        private Long id;
        private Map<String, Object> attributes;   // autopid_device_id etc.
        private Long groupId;
        private Long calendarId;
        private String name;
        private String uniqueId;
        private String status;
        private String lastUpdate;
        private Long positionId;
        private String phone;
        private String model;
        private String contact;
        private String category;
        private Boolean disabled;
        private String expirationTime;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        public Long getGroupId() {
            return groupId;
        }

        public void setGroupId(Long groupId) {
            this.groupId = groupId;
        }

        public Long getCalendarId() {
            return calendarId;
        }

        public void setCalendarId(Long calendarId) {
            this.calendarId = calendarId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public Long getPositionId() {
            return positionId;
        }

        public void setPositionId(Long positionId) {
            this.positionId = positionId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Boolean getDisabled() {
            return disabled;
        }

        public void setDisabled(Boolean disabled) {
            this.disabled = disabled;
        }

        public String getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(String expirationTime) {
            this.expirationTime = expirationTime;
        }
    }
}