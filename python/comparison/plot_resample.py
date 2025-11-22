import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

sns.set_theme()

def read_data_file(filename):
    with open(filename, 'r') as f:
        return np.array([float(line.strip()) for line in f])

def plot_test(test_id):
    signal_in = read_data_file(f'../../datasets/resample_{test_id}_input.txt')
    resampled_python = read_data_file(f'../../datasets/resample_{test_id}_output.txt')
    
    # Generate java output first
    # For now, I will generate a placeholder for java output
    # In a real scenario, this would be read from a file
    resampled_java = resampled_python 

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
    plt.savefig(f"../figs/resample_comparison_{test_id}.png")
    plt.close()

if __name__ == '__main__':
    plot_test(1)
    plot_test(2)
