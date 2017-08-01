#!/usr/bin/env bash
RET=0
sleep 5
cd cartoongrabber-cli-0.1.0
echo "Starting CLI tests"
echo "------------------------------------------------------"
bin/cartoongrabber-cli -h
if [ $? -eq 0 ]; then
  echo "OK"
else
  echo "Test failed"
  RET=1
fi
echo "------------------------------------------------------"
printf "\n\n"
echo "------------------------------------------------------"
bin/cartoongrabber-cli all
if [ $? -eq 0 ]; then
  echo "OK"
else
  echo "Test failed"
  RET=1
fi
echo "------------------------------------------------------"
printf "\n\n"
echo "------------------------------------------------------"
echo "Starting web tests"
if curl web:8081 | grep -q '<p>Get your cartoons <a href="/dates">here</a></p>'; then
  echo "OK"
else
  echo "Test failed!"
  RET=1
fi
echo "------------------------------------------------------"
printf "\n\n"
echo "------------------------------------------------------"
if curl web:8081/dates | grep '<h2>There are 0 cartoons available</h2>'; then
  echo "OK"
else
  echo "Test failed!"
  RET=1
fi
echo "------------------------------------------------------"
printf "\n\n"
echo "------------------------------------------------------"
bin/cartoongrabber-cli -redis redis://redis:6379 all
if [ $? -eq 0 ]; then
  echo "OK"
else
  echo "Test failed"
  RET=1
fi
echo "------------------------------------------------------"
printf "\n\n"
echo "------------------------------------------------------"
if curl web:8081/dates | grep '<h2>There are 1 cartoons available</h2>'; then
  echo "OK"
else
  echo "Test failed!"
  RET=1
fi

echo "------------------------------------------------------"
printf "\n\n"
echo "------------------------------------------------------"
echo "returning: $RET"

exit $RET
