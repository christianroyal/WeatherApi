package com.example.weatherapi;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class LocationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // register the event to listen.
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_user_xml, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListener(view);
    }

    public void setClickListener(final View view) {
        Button btnSubmit = (Button) view.findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMessage = (EditText) view.findViewById(R.id.editText);

                // We are broadcasting the message here to listen to the subscriber.
                MessageEvent.FragmentActivityMessage fragmentActivityMessageEvent =
                        new MessageEvent.FragmentActivityMessage(
                                String.valueOf(etMessage.getText()));
                EventBus.getDefault().post(fragmentActivityMessageEvent);
            }
        });
    }

    @Subscribe
    public void getMessage(MessageEvent.ActivityFragmentMessage activityFragmentMessage) {
        TextView messageView = (TextView) getView().findViewById(R.id.message);
        messageView.setText(
                String.format("%s %s", getString(R.string.message_received), activityFragmentMessage.getMessage()));

        Toast.makeText(getActivity(),
                getString(R.string.message_fragment) +
                        " " + activityFragmentMessage.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unregister the registered event.
        EventBus.getDefault().unregister(this);
    }
}