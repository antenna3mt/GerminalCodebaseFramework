package com.peruselab.client;

import com.peruselab.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
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

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		MaterialCard card = new MaterialCard();
		MaterialCardContent content = new MaterialCardContent();
		MaterialCardTitle cardTitle = new MaterialCardTitle();
		cardTitle.setText("GWT Material Design Test");
		content.add(new HTML("Hello GWT Material Design"));
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
