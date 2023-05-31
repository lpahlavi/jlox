package com.craftinginterpreters.shared;

/**
 * Error codes as defined in the
 * <a href="https://man.freebsd.org/cgi/man.cgi?query=sysexits&manpath=FreeBSD+4.3-RELEASE">UNIX sysexits.h file</a>.
 */
public final class ErrorCode {
    public static final int EX_USAGE = 64;        // The command was used incorrectly
    public static final int EX_DATAERR = 65;      // The input data was incorrect
    public static final int EX_NOINPUT = 66;      // An input file did not exist or was not readable
    public static final int EX_NOUSER = 67;       // The specified user did not exist
    public static final int EX_NOHOST = 68;       // The specified host did not exist
    public static final int EX_UNAVAILABLE = 69;  // A service is unavailable
    public static final int EX_SOFTWARE = 70;     // An internal software error has been detected
    public static final int EX_OSERR = 71;        // An operating system error has been detected
    public static final int EX_OSFILE = 72;       // A system file does not exist or has an error
    public static final int EX_CANTCREAT = 73;    // An output file cannot be created
    public static final int EX_IOERR = 74;        // An error occurred while doing I/O on a file
    public static final int EX_TEMPFAIL = 75;     // Temporary failure, indicating something that is not really an error
    public static final int EX_PROTOCOL = 76;     // The remote system returned something that was not possible during a protocol exchange
    public static final int EX_NOPERM = 77;       // Insufficient permission to perform the operation
    public static final int EX_CONFIG = 78;       // Found in an unconfigured or misconfigured state
}
