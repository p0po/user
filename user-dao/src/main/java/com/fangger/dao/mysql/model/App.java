package com.fangger.dao.mysql.model;

public class App {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app.Id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app.version
     *
     * @mbggenerated
     */
    private String version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app.url
     *
     * @mbggenerated
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app.describes
     *
     * @mbggenerated
     */
    private String describes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app.status
     *
     * @mbggenerated
     */
    private Byte status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app.Id
     *
     * @return the value of app.Id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app.Id
     *
     * @param id the value for app.Id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app.name
     *
     * @return the value of app.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app.name
     *
     * @param name the value for app.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app.version
     *
     * @return the value of app.version
     *
     * @mbggenerated
     */
    public String getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app.version
     *
     * @param version the value for app.version
     *
     * @mbggenerated
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app.url
     *
     * @return the value of app.url
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app.url
     *
     * @param url the value for app.url
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app.describes
     *
     * @return the value of app.describes
     *
     * @mbggenerated
     */
    public String getDescribes() {
        return describes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app.describes
     *
     * @param describes the value for app.describes
     *
     * @mbggenerated
     */
    public void setDescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app.status
     *
     * @return the value of app.status
     *
     * @mbggenerated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app.status
     *
     * @param status the value for app.status
     *
     * @mbggenerated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}