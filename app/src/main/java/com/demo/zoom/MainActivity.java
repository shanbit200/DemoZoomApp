package com.demo.zoom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.zoom.initsdk.InitAuthSDKCallback;
import com.demo.zoom.initsdk.InitAuthSDKHelper;
import com.demo.zoom.startjoinmeeting.UserLoginCallback;

import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;

public class MainActivity extends AppCompatActivity implements InitAuthSDKCallback,
        MeetingServiceListener, UserLoginCallback.ZoomDemoAuthenticationListener, View.OnClickListener {

    private ZoomSDK mZoomSDK;

    private EditText numberEdit;
    private EditText nameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mZoomSDK = ZoomSDK.getInstance();

        setContentView(R.layout.activity_main);

        numberEdit = findViewById(R.id.edit_join_number);
        nameEdit = findViewById(R.id.edit_join_name);

        InitAuthSDKHelper.getInstance().initSDK(this, this);

        if (mZoomSDK.isInitialized()) {
            ZoomSDK.getInstance().getMeetingService().addListener(this);
            ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(true);
        }

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onZoomSDKLoginResult(long result) {

    }

    @Override
    public void onZoomSDKLogoutResult(long result) {

    }

    @Override
    public void onZoomIdentityExpired() {

    }

    @Override
    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int i, int i1) {

    }

    @Override
    public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
        //Log.i(TAG, "onZoomSDKInitializeResult, errorCode=" + errorCode + ", internalErrorCode=" + internalErrorCode);

        if (errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
            Toast.makeText(this, "Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG).show();
        } else {
            ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(true);
            ZoomSDK.getInstance().getMeetingSettingsHelper().enableShowMyMeetingElapseTime(true);
            ZoomSDK.getInstance().getMeetingSettingsHelper().setVideoOnWhenMyShare(true);
            ZoomSDK.getInstance().getMeetingService().addListener(this);
            Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();
            if (mZoomSDK.tryAutoLoginZoom() == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
                UserLoginCallback.getInstance().addListener(this);
                //showProgressPanel(true);
            } else {
                //showProgressPanel(false);
            }
        }
    }

    public void onClickJoin(View view) {
        if(!mZoomSDK.isInitialized())
        {
            Toast.makeText(this,"Init SDK First",Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }

        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(false);
        } else {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(true);
        }
        String number = numberEdit.getText().toString();
        String name = nameEdit.getText().toString();

        JoinMeetingParams params = new JoinMeetingParams();
        params.meetingNo = number;
        params.displayName = name;
        ZoomSDK.getInstance().getMeetingService().joinMeetingWithParams(this, params);
    }

    @Override
    public void onZoomAuthIdentityExpired() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserLoginCallback.getInstance().removeListener(this);

        if(null!= ZoomSDK.getInstance().getMeetingService())
        {
            ZoomSDK.getInstance().getMeetingService().removeListener(this);
        }
        InitAuthSDKHelper.getInstance().reset();
    }
}
