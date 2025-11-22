import os
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

sns.set_theme()

script_dir = os.path.dirname(__file__)

def read_data_file(filename):
    absolute_path = os.path.join(script_dir, filename)
    with open(absolute_path, 'r') as f:
        data = [float(line.strip()) for line in f if line.strip()]
    return data

def plot_test(test_id):
    signal_in = read_data_file(f'../../datasets/resample_{test_id}_input.txt')
    resampled_python = read_data_file(f'../../datasets/resample_{test_id}_output.txt')
    
    resampled_java = read_data_file(f'../../datasets/resample_{test_id}_output_java.txt')

    # Time axes for plotting
    t_in = np.linspace(0, 1, len(signal_in))
    t_out = np.linspace(0, 1, len(resampled_python))

    # Plotting
    plt.figure(figsize=(12, 6))
    plt.plot(t_in, signal_in, 'o-', label='Original Signal')
    plt.plot(t_out, resampled_python, 's-', label='Python Resampled')
    plt.plot(t_out, resampled_java, 'x--', label='Java Resampled')
    plt.title(f"Resampling Comparison for test {test_id}")
    plt.xlabel("Time")
    plt.ylabel("Amplitude")
    plt.legend()

    plt.tight_layout()
    save_path = os.path.join(script_dir, f"../figs/resample_comparison_{test_id}.png")
    plt.savefig(save_path)
    plt.close()

if __name__ == '__main__':
    plot_test(1)
    plot_test(2)
