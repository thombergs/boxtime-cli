class Boxtime < Formula
  desc "Take control of your time by tracking your time-spending habits."
  homepage "https://github.com/thombergs/boxtime-cli"
  url "https://github.com/thombergs/boxtime-cli/releases/download/test/boxtime.tar.gz"
  sha256 "21d9ee0b582f096d165e35b5a68f247ddc78d127c96ad616e7b3e154979f58cb"
  license "MIT"
  version "0.0.1"

  # depends_on "cmake" => :build

  def install
    bin.install "boxtime"
  end
end