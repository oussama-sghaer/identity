package com.training.identity.domain;


public record Membership(UserId subject, TeamId team, Role role, MembershipStatus status ) {


    public Membership{
        if(subject == null) throw new IllegalArgumentException("Subject cannot be null");
        if(team == null) throw new IllegalArgumentException("Team cannot be null");
        if(role == null) throw new IllegalArgumentException("Role cannot be null");
        if(status == null) throw new IllegalArgumentException("Status cannot be null");
    }
    public boolean hasPermission(Permission permission){
        if(status != MembershipStatus.ACTIVE) return false;
        return role.hasPermission(permission);
    }
    public  Membership withStatus(MembershipStatus status){
        return new Membership(this.subject,this.team,this.role,status);
    }
}
