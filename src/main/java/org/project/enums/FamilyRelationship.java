package org.project.enums;

public enum FamilyRelationship {
    SELF("self"),
    FATHER("father"),
    MOTHER("mother"),
    BROTHER("brother"),
    SISTER("sister"),
    SON("son"),
    DAUGHTER("daughter"),
    GRANDFATHER("grandfather"),
    GRANDMOTHER("grandmother"),
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
