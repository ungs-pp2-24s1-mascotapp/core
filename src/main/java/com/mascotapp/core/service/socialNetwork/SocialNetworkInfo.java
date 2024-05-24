package com.mascotapp.core.service.socialNetwork;

import java.util.Objects;

public class SocialNetworkInfo {
	private String name;
	private boolean active;
	
	public SocialNetworkInfo(String name, boolean active) {
		this.name = name;
		this.active = active;
	}

	public String getName() {
		return name;
	}
	
	public boolean isActive() {
		return active;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(active, name);
	}
	
	@Override
	public String toString() {
		return name + (active ? " (Activo)" : " (Inactivo)");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SocialNetworkInfo other = (SocialNetworkInfo) obj;
		return active == other.active && Objects.equals(name, other.name);
	}
	
	
}
