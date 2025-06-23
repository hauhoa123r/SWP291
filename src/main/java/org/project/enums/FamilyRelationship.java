package org.project.enums;

public enum FamilyRelationship {
    SELF("self"),
    FATHER("father"),
    MOTHER("mother"),
    BROTHER("brother"),
    SISTER("sister"),
    SON("son"),
    DAUGHTER("daughter"),
    GRAND_FATHER("grandfather"),
    GRAND_MOTHER("grandmother"),
    COUSIN("cousin"),
    AUNT("aunt"),
    UNCLE("uncle"),
    OTHER("other");

    private final String relationship;

    FamilyRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRelationship() {
        return relationship;
    }
}
