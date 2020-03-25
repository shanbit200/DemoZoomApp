package com.demo.zoom.initsdk;

public interface AuthConstants {

	// TODO Change it to your web domain
	public final static String WEB_DOMAIN = "zoom.us";

	// TODO Change it to your APP Key
	public final static String SDK_KEY = "ciW2MLKmXQbyRJGR7Ei2mGUjgrAEGkEu7U98";

	// TODO Change it to your APP Secret
	public final static String SDK_SECRET = "OLVD6rChcqLSfgWTtNA62k1ZTy48EldzHEB2";

	/**
	 * We recommend that, you can generate jwttoken on your own server instead of hardcore in the code.
	 * We hardcore it here, just to run the demo.
	 *
	 * You can generate a jwttoken on the https://jwt.io/
	 * with this payload:
	 * {
	 *     "appKey": "string", // app key
	 *     "iat": long, // access token issue timestamp
	 *     "exp": long, // access token expire time
	 *     "tokenExp": long // token expire time
	 * }
	 */
	public final static String SDK_JWTTOKEN = "";

}
