package models;

public record Game
(
    int id,
    int idWordMaster,
    int idGuessMaster,
    int idGrid,
    String joinCode
) {}
