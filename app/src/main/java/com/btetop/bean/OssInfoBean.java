package com.btetop.bean;

public class OssInfoBean {

    /**
     * securityToken : CAISqQJ1q6Ft5B2yfSjIr4iBetvZjrZI7ZWnQFCJoGVgdb1ah7fa0Tz2IHFLfHdgB+8Zs/Q0lWtZ6PoflqV5V5IAXlDfKNZr444PVrJ28wKF6aKP9rUhpMCP8QHxYkeJz62
     * /SuH9S8ynV5XJQlvYlyh17KLnfDG5JTKMOoGIjpgVK7ZyWRKjPxVLGP1LLQhvtK1/MmDKZ9mgLjnggGfbECgNvRFn21ti9YO1wMCX9mCd7jvAx/QSup76L7W9csBoJ+0fadqu2/Fsfaezt0w14hNRpqBtl/4Gq3WV
     * /PPlWgcAuEXeb7KLqYMwfVQkNpJXQfAU8KLO8tRjofHWmojNzBJAAPpYSSy3Rvr7kZScRrP3aYlkJe2qYCWTjo2VWYLpqBkjZX8Lopin4iXZwxQagAEwnLea0TBz5ra5YXMN489uuIso6BuBetUVyaGxvsDeQ4QmhnKKE2TLpLnOFQzbf3o6Lty7rovGQ3W5IfMU7hB8/RuiGTbmRo60ggqnfwJ3FbpE8lQ1y5b9K/2mCEs2480rM+kvnOFSMOy2wkiD0FcJt/75A8rmywJyQejYzob7kA==
     * accessKeySecret : FfWjSrTPFkDMFAAVst1DvnyFjDT4T3f4cjSTADvtc2aj
     * accessKeyId : STS.NK41amciiZWLBv8De5y2vhrq3
     * expiration : 2018-09-25T04:49:53Z
     */

    private String securityToken;
    private String accessKeySecret;
    private String accessKeyId;
    private String expiration;

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
