import random
import time
import matplotlib.pyplot as plt
import numpy as np

def insertion_sort_analysis(array):
    """Perform insertion sort on the array and count the number of key comparisons."""
    comparison_count = 0
    n = len(array)
    for i in range(1, n):
        value = array[i]
        j = i - 1
        while j >= 0 and array[j] > value:
            comparison_count += 1
            array[j + 1] = array[j]
            j -= 1
        if j >= 0:
            comparison_count += 1
        array[j + 1] = value
    return comparison_count

def run_sort_experiment():
    """Run the insertion sort on arrays of various sizes and record the number of comparisons and running times."""
    array_sizes = [1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500]
    comparisons = []
    execution_times = []

    for size in array_sizes:
        array = [random.randint(0, 10000) for _ in range(size)]
        start_time = time.time()
        comparison_count = insertion_sort_analysis(array)
        end_time = time.time()
        comparisons.append(comparison_count)
        execution_times.append((end_time - start_time) * 1000)  # Convert to milliseconds

        print(f"Array Size: {size} | Key Comparisons: {comparison_count} | Execution Time (ms): {(end_time - start_time) * 1000}")

    return array_sizes, comparisons, execution_times

def plot_experiment_results(array_sizes, comparisons, execution_times):
    """Plot the number of comparisons and execution times against array sizes."""
    plt.figure(figsize=(12, 6))

    # Plot Key Comparisons vs Array Size
    plt.subplot(1, 2, 1)
    plt.scatter(array_sizes, comparisons, color='blue')
    plt.title('Key Comparisons vs Array Size')
    plt.xlabel('Array Size')
    plt.ylabel('Number of Key Comparisons')

    # Add trend line for key comparisons
    trend_line = np.polyfit(array_sizes, comparisons, 2)  # Quadratic fit for visualization
    polynomial = np.poly1d(trend_line)
    plt.plot(array_sizes, polynomial(array_sizes), "r--")

    # Plot Running Time vs Array Size
    plt.subplot(1, 2, 2)
    plt.scatter(array_sizes, execution_times, color='red')
    plt.title('Execution Time vs Array Size')
    plt.xlabel('Array Size')
    plt.ylabel('Execution Time (ms)')

    # Add trend line for execution times
    trend_line = np.polyfit(array_sizes, execution_times, 2)  # Quadratic fit for visualization
    polynomial = np.poly1d(trend_line)
    plt.plot(array_sizes, polynomial(array_sizes), "r--")

    plt.tight_layout()
    plt.show()

def main():
    """Main function to run the sorting experiment and plot the results."""
    array_sizes, comparisons, execution_times = run_sort_experiment()
    plot_experiment_results(array_sizes, comparisons, execution_times)

if __name__ == "__main__":
    main()
