const express = require('express');
const bodyParser = require('body-parser');
const axios = require('axios');
const { twiml } = require('twilio');

const app = express();
app.use(bodyParser.urlencoded({ extended: false }));

app.post('/sms', async (req, res) => {
  const incomingMsg = req.body.Body;
  const fromNumber = req.body.From;

  // Call your Retell agent here (or use OpenAI if you're proxying it)
  const reply = await getLLMReply(incomingMsg, fromNumber);

  const response = new twiml.MessagingResponse();
  response.message(reply);

  res.type('text/xml');
  res.send(response.toString());
});

async function getLLMReply(message, sessionId) {
  // Replace with your Retell or OpenAI call
  const response = await axios.post('https://your-retell-agent-endpoint', {
    message,
    sessionId,
  });

  return response.data.reply;
}

app.listen(3000, () => {
  console.log('Server is running on port 3000');
});
