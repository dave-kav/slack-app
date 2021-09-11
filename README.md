# Movie Info

## Running locally

To run the app locally use:
`gw clean bootRun`

### Design
`com.dk.slack.application.SlackApp` configures the Slack application instance, wiring the necessary
components together. The `Application` and `SlackAppController` classes in the same package configure 
the SpringBoot Application, and the base endpoint for `/slack/events`.

Requires the following ENV variables are set:
- MOVIE_DB_API_KEY
- SLACK_BOT_TOKEN
- SLACK_SIGNING_SECRET