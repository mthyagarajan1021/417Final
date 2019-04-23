#!/usr/bin/java --source 11
import java.util.Iterator;
import java.util.Random;

/**
 * This class represents the MonteCarlo part of the filter for the CSC 417 final
 * project.
 *
 * @author Maanasa Thyagarajan
 *
 */

public class monte_carlo {

    /** Attribute object defined in original code */
    private static Attribute                pomposity      = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                learning_curve = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                optimism       = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                atleast        = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                done_percent   = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                sDR_param1     = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                sDR_param2     = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                d              = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                ep             = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                nprod          = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                np             = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                ts             = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                to             = new Attribute();
    /** Attribute object defined in original MonteCarlo code */
    private static Attribute                r              = new Attribute();
    /** Array of Attribute objects stored in this instance of MonteCarlo */
    private static Attribute                items[];
    /** AttributeList object which contains all the defined Attribute objects */
    private static AttributeList<Attribute> attributes;
    /** Number of times to repeat generation of values based on command input */
    private static int                      repeats;
    /** Seed value to use to generate random numbers */
    private static int                      seed;
    /**
     * Boolean true/false value used for the next part of the pipe- not actually
     * used to complete operations with MonteCarlo
     */
    @SuppressWarnings ( "unused" )
    private static boolean                  verbosity;

    /**
     * Constructor of MonteCarlo instance, initializes values of each Attribute
     * object
     */
    public monte_carlo () {
        pomposity.min = 0;
        pomposity.max = 1;
        pomposity.name = "pomposity";

        learning_curve.min = 1;
        learning_curve.max = 100;
        learning_curve.name = "learning_curve";

        optimism.min = (float) 0.1;
        optimism.max = 10;
        optimism.name = "optimism";

        atleast.min = 0;
        atleast.max = 100;
        atleast.name = "atleast";

        done_percent.min = 0;
        done_percent.max = 100;
        done_percent.name = "done_percent";

        sDR_param1.min = 0;
        sDR_param1.max = 1;
        sDR_param1.name = "sDR_param1";

        sDR_param2.min = 1;
        sDR_param2.max = 10;
        sDR_param2.name = "sDR_param2";

        d.min = 0;
        d.max = 90;
        d.name = "d";

        ep.min = 1;
        ep.max = 30;
        ep.name = "ep";

        nprod.min = (float) 0.1;
        nprod.max = 1;
        nprod.name = "nprod";

        np.min = 1;
        np.max = 30;
        np.name = "np";

        ts.min = 1;
        ts.max = 10;
        ts.name = "ts";

        to.min = 1;
        to.max = 100;
        to.name = "to";

        r.min = 100;
        r.max = 1000;
        r.name = "r";

    } // closes MonteCarlo constructor

    /**
     * Generator function that represents the Generator abstraction. Takes in
     * the instance of MonteCarlo as a parameter and generates each value up
     * iteration of the instance's AttributeList.
     *
     * @param mc
     *            Instance of MonteCarlo being used
     */
    public static void generator ( final monte_carlo mc ) {

        mc.items = new Attribute[] { pomposity, learning_curve, optimism, atleast, done_percent, sDR_param1, sDR_param2,
                d, ep, nprod, np, ts, to, r }; // initializes the array holding
                                               // Attribute objects

        mc.attributes = new AttributeList<Attribute>( items ); // Creates the
                                                               // AttributeList
                                                               // with given
                                                               // array

        final Random rand = new Random( seed ); // generates random number with
                                                // the given seed

        for ( int i = 0; i < repeats; i++ ) { // Loops through as many times as
                                              // pipe input dictates in
                                              // "repeats"
            final Iterator<Attribute> iterator = mc.attributes.iterator(); // Creates
                                                                           // instance
                                                                           // of
                                                                           // iterator
                                                                           // object

            final StringBuilder s = new StringBuilder(); // Creates
                                                         // StringBuilder to use
                                                         // for the final output
            s.append( '{' );

            while ( iterator.hasNext() ) { // while there are more items in the
                                           // list

                final Attribute current = iterator.next(); // Currently accessed
                                                           // Attribute

                final double minimum = current.min; // Min value of the
                                                    // currently access
                                                    // Attribute
                final double maximum = current.max; // Max value of the
                                                    // currently access
                                                    // Attribute

                // Generates a random number within the given range
                final double randomNumber = rand.nextDouble() * ( maximum - minimum ) + minimum;

                // Converts random number to a string that prints double value
                // to 2 decimal places
                final String randString = String.format( "%.2f", randomNumber );

                s.append( current.name + ":" + randString + "," );

            }
            s.replace( s.length() - 1, s.length(), "}" );
            System.out.println( s );
            s.setLength( 0 ); // resets the string

        }

    }

    /**
     * Main method that starts the program. Accepts command line arguments as
     * parameters and uses that input to set field values. Also initializes the
     * instance of MonteCarlo being used
     *
     * @param args
     */
    public static void main ( final String[] args ) {

        final monte_carlo mc = new monte_carlo(); // initializes instance of
                                                // MonteCarlo

        repeats = Integer.parseInt( args[1] );
        seed = Integer.parseInt( args[3] );
        final String verbosityString = args[5];

        if ( verbosityString.toLowerCase().contains( "true" ) ) { // sets value
                                                                  // of
                                                                  // verbosity
            verbosity = true;
        }
        else {
            verbosity = false;
        }

        generator( mc ); // calls generator to yield each necessary value

    }

}

/**
 * This class represents an Attribute object, which is used by MonteCarlo and is
 * the "part" of the "whole" program. Main representation of the Composite
 * abstraction
 *
 * @author Maanasa Thyagarajan
 *
 */
class Attribute {

    /** Min value of given Attribute object */
    double min;
    /** Max value of given Attribute object */
    double max;
    /** Name of given Attribute object */
    String name;

    /**
     * Constructor of Attribute object, initializes the fields
     */
    public Attribute () {
        this.min = min;
        this.max = max;
        this.name = name;
    }
}

/**
 * AttributeList class used to implement the Iterator abstraction used in
 * MonteCarlo
 *
 * @author Maanasa Thyagarajan
 *
 * @param <Attribute>
 *            Attribute object contained in AttributeList
 */
class AttributeList <Attribute> implements Iterable<Attribute> {

    /** Array of Attribute objects used to initialize the AttributeList */
    private Attribute[] aList;
    /** Current size of AttributeList */
    private int         currentSize;

    /**
     * Constructor of AttributeList, accepts an array as a parameter to build
     * the AttributeList with
     *
     * @param newArray
     *            array used to construct the AttributeList
     */
    public AttributeList ( final Attribute[] newArray ) {
        this.aList = newArray;
        this.currentSize = newArray.length;
    }

    /**
     * This method allows Attribute objects to be added to the given index
     *
     * @param index,
     *            index at which to add Attribute object
     * @param attr,
     *            Attribute object to be added to list
     */
    public void add ( final int index, final Attribute attr ) {

        if ( attr == null ) { // throws exception if passed in object is null
            throw new NullPointerException();
        }

        if ( ( index > currentSize ) || index < 0 ) { // throws exception if the
                                                      // list cannot accept more
                                                      // objects
            throw new IndexOutOfBoundsException();
        }

        if ( currentSize + 1 > aList.length ) { // makes call to grow list so it
                                                // can accept more objects
            growArray();
        }

        for ( int i = currentSize - 1; i >= index; i-- ) { // adds object to
                                                           // list
            aList[i + 1] = aList[i];
        }
        aList[index] = attr;
        currentSize++;
    }

    /**
     * This method grows the AttributeList so it can accept more objects
     */
    @SuppressWarnings ( "unchecked" )
    private void growArray () {

        final Attribute[] newList = (Attribute[]) new Object[currentSize * 2];

        for ( int i = 0; i < currentSize; i++ ) {
            newList[i] = aList[i];
        }
        aList = newList;
    }

    /**
     * Overridden Iterable method that constructs the Iterator object
     */
    @Override
    public Iterator<Attribute> iterator () {
        final Iterator<Attribute> iterator = new Iterator<Attribute>() {

            private int idx = 0;

            /**
             * Overridden method that checks if the collection being iterated on
             * has another object
             */
            @Override
            public boolean hasNext () {
                return ( idx < currentSize ) && ( aList[idx] != null );
            }

            /**
             * Overridden method that traverses to the next method of the given
             * collection
             */
            @Override
            public Attribute next () {
                return aList[idx++];
            }

            /**
             * Overridden method that allows objects to be removed from given
             * collection
             */
            @Override
            public void remove () {
                throw new UnsupportedOperationException();
            }
        };
        return iterator;
    }
}
