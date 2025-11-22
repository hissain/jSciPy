package com.hissain.jscipy.signal;

import com.hissain.jscipy.signal.api.IFFT;
import com.hissain.jscipy.signal.api.IResample;
import com.hissain.jscipy.signal.fft.FFT;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;

public class Resample implements IResample {

    private final IFFT fft;

    public Resample() {
        this.fft = new FFT();
    }

    @Override
    public double[] resample(double[] signal, int num) {
        int len = signal.length;
        if (len == 0) {
            return new double[0];
        }

        Complex[] spectrum = fft.fft(signal);
        Complex[] resampledSpectrum = new Complex[num];
        Arrays.fill(resampledSpectrum, Complex.ZERO);

        if (num > len) { // Upsampling
            int halfLen = (len + 1) / 2;
            System.arraycopy(spectrum, 0, resampledSpectrum, 0, halfLen);

            int srcPos = halfLen;
            int destPos = num - (len - halfLen);
            System.arraycopy(spectrum, srcPos, resampledSpectrum, destPos, len - halfLen);
        } else { // Downsampling
            int halfNum = (num + 1) / 2;
            System.arraycopy(spectrum, 0, resampledSpectrum, 0, halfNum);

            int srcPos = len - (num - halfNum);
            int destPos = halfNum;
            System.arraycopy(spectrum, srcPos, resampledSpectrum, destPos, num - halfNum);
        }

        Complex[] ifftResult = fft.ifft(resampledSpectrum);
        double[] realResult = new double[num];
        double scale = (double) num / len;
        for (int i = 0; i < num; i++) {
            realResult[i] = ifftResult[i].getReal() * scale;
        }

        return realResult;
    }
}
