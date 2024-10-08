///*
// * Copyright (C) 2013 Google Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.google.example.games.basegameutils;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//
////import com.google.android.gms.common.api.GoogleApiClient;
//
///**
// * Example base class for games. This implementation takes care of setting up
// * the API client object and managing its lifecycle. Subclasses only need to
// * override the @link{#onSignInSucceeded} and @link{#onSignInFailed} abstract
// * methods. To initiate the sign-in flow when the user clicks the sign-in
// * button, subclasses should call @link{#beginUserInitiatedSignIn}. By default,
// * this class only instantiates the GoogleApiClient object. If the PlusClient or
// * AppStateClient objects are also wanted, call the BaseGameActivity(int)
// * constructor and specify the requested clients. For example, to request
// * PlusClient and GamesClient, use BaseGameActivity(CLIENT_GAMES | CLIENT_PLUS).
// * To request all available clients, use BaseGameActivity(CLIENT_ALL).
// * Alternatively, you can also specify the requested clients via
// *
// * @author Bruno Oliveira (Google)
// * @link{#setRequestedClients}, but you must do so before @link{#onCreate}
// * gets called, otherwise the call will have no effect.
// */
//public abstract class BaseGameActivity extends FragmentActivity implements
//        GameHelper.GameHelperListener {
//
//    // The game helper object. This class is mainly a wrapper around this object.
//    protected GameHelper mHelper;
//
//    // We expose these constants here because we don't want users of this class
//    // to have to know about GameHelper at all.
//    public static final int CLIENT_GAMES = GameHelper.CLIENT_GAMES;
//    public static final int CLIENT_APPSTATE = GameHelper.CLIENT_APPSTATE;
//    public static final int CLIENT_PLUS = GameHelper.CLIENT_PLUS;
//    public static final int CLIENT_ALL = GameHelper.CLIENT_ALL;
//
//    // Requested clients. By default, that's just the games client.
//    protected int mRequestedClients = CLIENT_GAMES;
//
//    protected boolean mDebugLog = false;
//
//    /** Constructs a BaseGameActivity with default client (GamesClient). */
//    protected BaseGameActivity() {
//        super();
//    }
//
//    /**
//     * Constructs a BaseGameActivity with the requested clients.
//     *
//     * @param requestedClients The requested clients (a combination of CLIENT_GAMES,
//     *                         CLIENT_PLUS and CLIENT_APPSTATE).
//     */
//    protected BaseGameActivity(int requestedClients) {
//        super();
//        setRequestedClients(requestedClients);
//    }
//
//    /**
//     * Sets the requested clients. The preferred way to set the requested clients is
//     * via the constructor, but this method is available if for some reason your code
//     * cannot do this in the constructor. This must be called before onCreate or getGameHelper()
//     * in order to have any effect. If called after onCreate()/getGameHelper(), this method
//     * is a no-op.
//     *
//     * @param requestedClients A combination of the flags CLIENT_GAMES, CLIENT_PLUS
//     *                         and CLIENT_APPSTATE, or CLIENT_ALL to request all available clients.
//     */
//    protected void setRequestedClients(int requestedClients) {
//        mRequestedClients = requestedClients;
//    }
//
//    public GameHelper getGameHelper() {
//        if (mHelper == null) {
//            mHelper = new GameHelper(this, mRequestedClients);
//            mHelper.enableDebugLog(mDebugLog);
//        }
//        return mHelper;
//    }
//
//    @Override
//    protected void onCreate(Bundle b) {
//        super.onCreate(b);
////        if (mHelper == null) {
////            getGameHelper();
////        }
////        mHelper.setup(this);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mHelper.onStart(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mHelper.onStop();
//    }
//
//    @Override
//    protected void onActivityResult(int request, int response, Intent data) {
//        super.onActivityResult(request, response, data);
//        mHelper.onActivityResult(request, response, data);
//    }
//
//    protected GoogleApiClient getApiClient() {
//        return mHelper.getApiClient();
//    }
//
//    protected boolean isSignedIn() {
//        return mHelper.isSignedIn();
//    }
//
//    protected void beginUserInitiatedSignIn() {
//        mHelper.beginUserInitiatedSignIn();
//    }
//
//    protected void signOut() {
//        mHelper.signOut();
//    }
//
//    protected void showAlert(String message) {
//        mHelper.makeSimpleDialog(message).show();
//    }
//
//    protected void showAlert(String title, String message) {
//        mHelper.makeSimpleDialog(title, message).show();
//    }
//
//    protected void enableDebugLog(boolean enabled) {
//        mDebugLog = true;
//        if (mHelper != null) {
//            mHelper.enableDebugLog(enabled);
//        }
//    }
//
//    /** @deprecated Use enableDebugLog(boolean) */
//    @Deprecated
//    protected void enableDebugLog(boolean enabled, String tag) {
//        Log.w(tag, "BaseGameActivity.enabledDebugLog(bool,String) is deprecated. Use enableDebugLog(boolean)");
//        enableDebugLog(enabled);
//    }
//
//    protected String getInvitationId() {
//        return mHelper.getInvitationId();
//    }
//
//    protected void reconnectClient() {
//        mHelper.reconnectClient();
//    }
//
//    protected boolean hasSignInError() {
//        return mHelper.hasSignInError();
//    }
//
//    protected GameHelper.SignInFailureReason getSignInError() {
//        return mHelper.getSignInError();
//    }
//
//    // This snippet hides the system bars.
//    private void hideSystemUI(View decorView) {
//
//        // Set the IMMERSIVE flag.
//        // Set the content to appear under the system bars so that the content
//        // doesn't resize when the system bars hide and show.
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
//    }
//
//    // This snippet shows the system bars. It does this by removing all the flags
//// except for the ones that make the content appear under the system bars.
//    private void showSystemUI() {
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//    }
//}
