package com.hissain.jscipy.signal.fft;

import com.hissain.jscipy.signal.api.IFFT;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.Arrays;

public class FFT implements IFFT {

    private final FastFourierTransformer transformer;

    public FFT() {
        this.transformer = new FastFourierTransformer(DftNormalization.STANDARD);
    }

    private int nextPowerOf2(int n) {
        if (n == 0) return 1;
        int power = 1;
        while (power < n) {
            power <<= 1;
        }
        return power;
    }

    @Override
    public Complex[] fft(double[] input) {
        int n = input.length;
        int paddedN = nextPowerOf2(n);
        double[] paddedInput = Arrays.copyOf(input, paddedN); // Pad with zeros

        Complex[] transformResult = transformer.transform(paddedInput, TransformType.FORWARD);

        // Truncate result to original length, but keep full spectrum if n is not power of 2
        // For FFT, typically we want the full spectrum corresponding to the original signal length
        return Arrays.copyOf(transformResult, n);
    }

    @Override
    public Complex[] rfft(double[] input) {
        Complex[] fftResult = fft(input);
        int n = input.length;
        int resultSize = n / 2 + 1;
        return Arrays.copyOf(fftResult, resultSize);
    }

    @Override
    public Complex[] ifft(Complex[] input) {
        int n = input.length;
        int paddedN = nextPowerOf2(n);
        Complex[] paddedInput = new Complex[paddedN];
        System.arraycopy(input, 0, paddedInput, 0, n);
        // Fill the rest with Complex.ZERO
        for (int i = n; i < paddedN; i++) {
            paddedInput[i] = Complex.ZERO;
        }

        Complex[] transformResult = transformer.transform(paddedInput, TransformType.INVERSE);

        double scale = (double) paddedN / n; // Adjust for padding effect
        // Truncate result to original length and apply scaling
        Complex[] result = new Complex[n];
        for (int i = 0; i < n; i++) {
            result[i] = new Complex(transformResult[i].getReal() * scale, transformResult[i].getImaginary() * scale);
        }
        return result;
    }

    @Override
    public double[] irfft(Complex[] input, int n) {
        // Reconstruct the full complex spectrum from the RFFT output
        Complex[] fullSpectrum = new Complex[n];
        fullSpectrum[0] = input[0]; // DC component

        for (int i = 1; i < input.length - 1; i++) {
            fullSpectrum[i] = input[i];
            fullSpectrum[n - i] = input[i].conjugate();
        }

        if (n % 2 == 0) { // Even length input
            fullSpectrum[n / 2] = input[n / 2]; // Nyquist component
        } else { // Odd length input
            // For odd length, the last element of rfft is the Nyquist component,
            // which doesn't have a corresponding negative frequency.
            // The loop above handles this correctly for n-i.
        }

        // Use the modified ifft that handles padding
        Complex[] ifftResult = ifft(fullSpectrum);
        double[] realResult = new double[n];
        for (int i = 0; i < n; i++) {
            realResult[i] = ifftResult[i].getReal();
        }
        return realResult;
    }
}