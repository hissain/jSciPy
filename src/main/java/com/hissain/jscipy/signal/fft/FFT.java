package com.hissain.jscipy.signal.fft;

import com.hissain.jscipy.signal.api.IFFT;
import org.jtransforms.fft.DoubleFFT_1D;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;

public class FFT implements IFFT {

    @Override
    public Complex[] fft(double[] input) {
        int n = input.length;
        DoubleFFT_1D fft = new DoubleFFT_1D(n);
        double[] a = new double[n * 2];
        System.arraycopy(input, 0, a, 0, n);
        fft.realForwardFull(a);
        Complex[] result = new Complex[n];
        for (int i = 0; i < n; i++) {
            result[i] = new Complex(a[2 * i], a[2 * i + 1]);
        }
        return result;
    }

    @Override
    public Complex[] rfft(double[] input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Complex[] ifft(Complex[] input) {
        int n = input.length;
        DoubleFFT_1D fft = new DoubleFFT_1D(n);
        double[] a = new double[n * 2];
        for (int i = 0; i < n; i++) {
            a[2 * i] = input[i].getReal();
            a[2 * i + 1] = input[i].getImaginary();
        }
        fft.complexInverse(a, true);
        Complex[] result = new Complex[n];
        for (int i = 0; i < n; i++) {
            result[i] = new Complex(a[2 * i], a[2 * i + 1]);
        }
        return result;
    }

    @Override
    public double[] irfft(Complex[] input, int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}