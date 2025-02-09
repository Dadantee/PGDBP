package com.inversion.pgdbp.user.routes;

public class UserRoutes {
    private final static String ROOT = "/api/v1/user";

    public final static String CREATE = ROOT;
    public final static String GET = ROOT + "/{id}";
    public final static String SEARCH = ROOT;
    public final static String BY_ID = ROOT + "/{id}";
}
