package consultorio.p2.servidores2.dto.auth;


public record AuthResponse(
    String token,
    String role
) {}

