import java.math.BigInteger;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class NTP {

    public static void main(String[] args)
    {

        // epoch prime 01/01/1900
        Instant epoch1900 = OffsetDateTime.of(1900, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toInstant();

        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss.SSSSSS ").withZone(ZoneOffset.UTC);

        BigInteger[] hexToDecimal = new BigInteger[6];

        // 2^-32 timestamp precision
        hexToDecimal[0] = new BigInteger("C50204ECEC42EE92", 16);
        hexToDecimal[1] = new BigInteger("C50204EBD3E8DDA4", 16);
        hexToDecimal[2] = new BigInteger("C50204EBD40C521D", 16);
        hexToDecimal[3] = new BigInteger("C50204ED2444AB18", 16);

        // epoch 1900
        hexToDecimal[4] = new BigInteger("0000000000000000", 16);
        // ntp reset
        hexToDecimal[5] = new BigInteger("FFFFFFFFFFFFFFFF", 16);


        System.out.println("");

        int tNumber = 1;
        for(int i = 0; i<hexToDecimal.length;i++) {
            // For seconds and fractional,  divide by 2^32 (4294967296)
            double toSeconds = hexToDecimal[i].doubleValue() / 4294967296.00;

            //System.out.println(toSeconds);

            // Convert from seconds to nanoseconds for precision
            // 10^-9 - loses precision from original 2^-32 timestamp. Original time stamp 4x precision of generated one
            // lose fractional from 9 places to 6
            Instant ntpTimestampNano = epoch1900.plusNanos(Math.round(toSeconds * 1000000000));

            // printing t1 to t4
            if(tNumber <= 4) {
                System.out.println("T" + tNumber + ": " + formatDateTime.format(ntpTimestampNano) + "UTC");
                System.out.println("");
            }
            else{
                System.out.println("    "+ formatDateTime.format(ntpTimestampNano) + "UTC");

            }
            tNumber++;
        }

    }

}

