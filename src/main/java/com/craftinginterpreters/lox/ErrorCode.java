package com.craftinginterpreters.lox;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/**
 * Error codes as defined in the
 * <a href="https://man.freebsd.org/cgi/man.cgi?query=sysexits&manpath=FreeBSD+4.3-RELEASE">UNIX sysexits.h file</a>.
 */
@AllArgsConstructor(access = PRIVATE)
@Getter
public enum ErrorCode {
    EX_USAGE(64),        // The command was used incorrectly
    EX_DATAERR(65),      // The input data was incorrect
    EX_NOINPUT(66),      // An input file did not exist or was not readable
    EX_NOUSER(67),       // The specified user did not exist
    EX_NOHOST(68),       // The specified host did not exist
    EX_UNAVAILABLE(69),  // A service is unavailable
    EX_SOFTWARE(70),     // An internal software error has been detected
    EX_OSERR(71),        // An operating system error has been detected
    EX_OSFILE(72),       // A system file does not exist or has an error
    EX_CANTCREAT(73),    // An output file cannot be created
    EX_IOERR(74),        // An error occurred while doing I/O on a file
    EX_TEMPFAIL(75),     // Temporary failure, indicating something that is not really an error
    EX_PROTOCOL(76),     // The remote system returned something that was not possible during a protocol exchange
    EX_NOPERM(77),       // Insufficient permission to perform the operation
    EX_CONFIG(78);       // Found in an unconfigured or misconfigured state

    private final int code;
}
