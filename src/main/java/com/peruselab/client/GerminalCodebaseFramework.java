package com.peruselab.client;

import com.peruselab.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import gwt.material.design.client.ui.*;
import thothbot.parallax.core.client.RenderingPanel;
import gwt.material.design.client.constants.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GerminalCodebaseFramework implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private Widget TalkPanel() {
		FlowPanel mainPanel = new FlowPanel();
		final MaterialTextBox nameField = new MaterialTextBox();
		nameField.setLabel("Name");
		nameField.setFocus(true);
		nameField.setText("GWT User");
		final MaterialButton sendButton = new MaterialButton("Send");
		final MaterialModal dialog = new MaterialModal();
		MaterialModalContent dialogContent = new MaterialModalContent();
		MaterialModalFooter dialogFooter = new MaterialModalFooter();
		FlowPanel dialogVPanel = new FlowPanel();
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogContent.add(dialogVPanel);
		final MaterialButton closeButton = new MaterialButton("Close");
		dialogFooter.add(closeButton);
		dialog.add(dialogContent);
		dialog.add(dialogFooter);
		dialog.close();
		mainPanel.add(nameField);
		mainPanel.add(sendButton);
		mainPanel.add(dialog);
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialog.close();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}

		});
		class MyHandler implements ClickHandler, KeyUpHandler {
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			private void sendNameToServer() {
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					MaterialToast.fireToast("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						MaterialToast.fireToast("Remote Procedure Call - Failure");

						serverResponseLabel.setHTML(SERVER_ERROR);
						dialog.close();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						MaterialToast.fireToast("Remote Procedure Call");

						serverResponseLabel.setHTML(result);
						dialog.open();
						closeButton.setFocus(true);
					}
				});

			}

		}
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
		return mainPanel;
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		MaterialCard card = new MaterialCard();
		MaterialCardContent content = new MaterialCardContent();
		MaterialCardTitle cardTitle = new MaterialCardTitle();
		cardTitle.setText("Talk to Server");
		content.add(TalkPanel());
		card.add(cardTitle);
		card.add(content);

		RenderingPanel renderingPanel = new RenderingPanel();
		renderingPanel.setBackground(0xcccccc);
		renderingPanel.setAnimatedScene(new ParallaxScene());
		renderingPanel.setHeight("800px");

		RootPanel.get("root").add(card);
		RootPanel.get("root").add(renderingPanel);

	}
}
