# Use a base image with Java 17
FROM eclipse-temurin:17-jdk-jammy

# Install necessary tools and dependencies
RUN apt-get update && \
    apt-get install -y maven wget gnupg unzip curl software-properties-common

# Add Mozilla Firefox repository and install Firefox
RUN add-apt-repository ppa:mozillateam/ppa && \
    apt-get update && \
    apt-get install -y firefox

# Install GeckoDriver
RUN wget https://github.com/mozilla/geckodriver/releases/download/v0.33.0/geckodriver-v0.33.0-linux64.tar.gz && \
    tar -xvzf geckodriver-v0.33.0-linux64.tar.gz && \
    chmod +x geckodriver && \
    mv geckodriver /usr/local/bin/ && \
    rm geckodriver-v0.33.0-linux64.tar.gz

# Install Chrome
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable

# Install ChromeDriver (using a fixed version)
RUN wget -q -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/94.0.4606.61/chromedriver_linux64.zip && \
    unzip /tmp/chromedriver.zip -d /usr/local/bin/ && \
    rm /tmp/chromedriver.zip && \
    chmod +x /usr/local/bin/chromedriver

# Set the working directory in the container
WORKDIR /usr/src/app

# Create a non-root user
RUN useradd -m myuser

# Copy the project files into the container
COPY --chown=myuser:myuser pom.xml .
COPY --chown=myuser:myuser src ./src

# Make sure the logs and test-output directories exist and are owned by myuser
RUN mkdir -p ./logs ./test-output ./target && \
    chown -R myuser:myuser ./logs ./test-output ./target

# Set environment variables
ENV FIREFOX_BROWSER=firefox
ENV CHROME_BROWSER=chrome
ENV BASE_URL=https://www.saucedemo.com/
ENV EXTENT_REPORT_PATH=test-output/ExtentReport.html

# Switch to the non-root user
USER myuser

# Download dependencies
RUN mvn dependency:go-offline

# Run tests
CMD ["mvn", "test"]