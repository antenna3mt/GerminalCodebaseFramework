package com.peruselab.client;

import thothbot.parallax.core.client.AnimatedScene;
import thothbot.parallax.core.shared.cameras.*;
import thothbot.parallax.core.shared.core.*;
import thothbot.parallax.core.shared.geometries.*;
import thothbot.parallax.core.shared.lights.*;
import thothbot.parallax.core.shared.materials.*;
import thothbot.parallax.core.shared.math.*;
import thothbot.parallax.core.shared.objects.*;

import java.math.*;

import com.google.gwt.event.dom.client.*;

public class ParallaxScene extends AnimatedScene {
	final double perspectiveFov = 35;
	final double perspectiveNear = 0.1;
	final double perspectiveFar = 3000;
	final Vector3 cameraPosition = new Vector3(0, 0, 1000);
	final Vector3 cameraLookAt = new Vector3(0, 0, 0);
	final int PointLightColor = 0xffffff;
	final double PointLightIntensity = 0.5;
	final Vector3 PointLightPos = new Vector3(1000, 1000, 400);
	final int AmbientLightColor = 0x777777;
	final double AmbientLightIntensity = 0.6;
	final Vector3 AmbientLightPos = new Vector3(0, 0, 1000);
	final int sphereWidthSegments = 32;
	final int sphereHeightSegments = 32;
	final int sphereColor = 0x88eeff;
	final double sphereRadius = 30;
	final int cylinderRadiusSegments = 32;
	final int cylinderHeightSegments = 1;
	final int cylinderColor = 0x88eeff;
	final double cylinderRadius = 15;

	protected void drawShpere(double radius, double x, double y, double z) {
		SphereGeometry geometry = new SphereGeometry(radius, sphereWidthSegments, sphereHeightSegments);
		MeshLambertMaterial material = new MeshLambertMaterial();
		material.setAmbient(new Color(sphereColor));
		Mesh sphere = new Mesh(geometry, material);
		sphere.setPosition(new Vector3(x, y, z));
		getScene().add(sphere);
	}

	protected void drawSylinder(double radius, double x1, double y1, double x2, double y2) {
		Geometry geometry = new CylinderGeometry(radius, radius, Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)),
				cylinderRadiusSegments, cylinderHeightSegments);
		MeshLambertMaterial material = new MeshLambertMaterial();
		material.setAmbient(new Color(cylinderColor));
		Mesh cylinder = new Mesh(geometry, material);
		cylinder.getRotation().addZ(Math.PI - Math.atan((x1 - x2) / (y1 - y2)));
		cylinder.setPosition(new Vector3((x1 + x2) / 2, (y1 + y2) / 2, 0));
		getScene().add(cylinder);
	}

	protected Vector3 screenProjectOn3D(double x, double y) {
		Vector3 vector = new Vector3();
		vector.set((x / getRenderer().getAbsoluteWidth()) * 2 - 1, -(y / getRenderer().getAbsoluteHeight()) * 2 + 1,
				0.5);
		vector.unproject(camera);
		Vector3 direction = vector.sub(camera.getPosition()).normalize();
		double distance = -camera.getPosition().getZ() / direction.getZ();
		return camera.getPosition().clone().add(direction.multiply(distance));
	}

	protected void drawKey(double x1, double y1, double x2, double y2) {
		Vector3 pos1 = screenProjectOn3D(x1, y1);
		Vector3 pos2 = screenProjectOn3D(x2, y2);
		drawShpere(sphereRadius, pos1.getX(), pos1.getY(), 0);
		drawShpere(sphereRadius, pos2.getX(), pos2.getY(), 0);
		drawSylinder(cylinderRadius, pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
	}

	private PerspectiveCamera camera;
	private int startX, startY, endX, endY;

	@Override
	protected void onStart() {
		camera = new PerspectiveCamera(perspectiveFov, getRenderer().getAbsoluteAspectRation(), perspectiveNear,
				perspectiveFar);
		camera.setPosition(cameraPosition);
		camera.lookAt(cameraLookAt);

		PointLight light = new PointLight(PointLightColor, PointLightIntensity);
		light.setPosition(PointLightPos);
		getScene().add(light);

		AmbientLight light1 = new AmbientLight(AmbientLightColor);
		light.setPosition(AmbientLightPos);
		getScene().add(light1);

		getCanvas().addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				startX = event.getClientX() - getCanvas().getAbsoluteLeft();
				startY = event.getClientY() - getCanvas().getAbsoluteTop();
			}
		});
		getCanvas().addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				endX = event.getClientX() - getCanvas().getAbsoluteLeft();
				endY = event.getClientY() - getCanvas().getAbsoluteTop();
				drawKey(startX, startY, endX, endY);
			}
		});
	}

	@Override
	protected void onUpdate(double duration) {
		getRenderer().render(getScene(), camera);
	}
}