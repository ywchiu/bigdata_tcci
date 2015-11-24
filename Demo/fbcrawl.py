# -*- coding: utf-8 -*-
import requests
import json
token = 'CAACEdEose0cBAC4MyGWpSdt123WDmEncv7ZC9jC9ZAd7aZBf2IdZAzVKyN7LazbIwYvYPqXWx31HY7U8kXBZBo5Sb9hxANIZAByEUJ9DRtNZBHu1hVSZASBkW9GDEVAIIsrnXLNjYghZA0F8m3mVmQb6SiieD6WeKcDaidS92txDXhhsvkRWP8gbIkxOCPTf0lkeUyzQ4s6gpLyEC0aEKguOqxUq5cnZAlcCgu7eqcOymyLAZDZD'
#token = 'CAACEdEose0cBAIj0IIJxn5jOcN8jgQh4bZAc0aZBOA3yHXidRcNRZCTI1gZAZCIHEIIXd6i1wnTgjAmE5uapyikiTZBoWisKkGUwMrFisBXe7DDunTTNpymLSv5vjFQmyUzvgkUYZAmj0pGZCMLGO2HEwZCKSvGJOeBBbpmFiHwbATi7oyONc7mts0yBJ8usuv1cuJ8ZCPXZBHpzIQ5n5cDQsKj8LWDdnv7gXKoPuhjjol2iAZDZD'
#token = 'CAACEdEose0cBAPpNA8MysD4WEhqZAw87gmtAbO4Q38gmhMTLGapZAbC0sP5U3KGTrG7CrU0RqYWrSZApjsNKkA7y7pSuH7kajJSOEqwo3cSrBcL26OJnuEZCGbARIISHBb71tr8wgZCcCRRYxyAGfySgZAvAidUzkCme5mgSIt3BoWM9JlG4qxR3LCZBV8lJjX6kqj0zZB3dYdqAZA0I64hLAi4ZBx0CIY6ZCTWHSyLYmIUHgZDZD'
url = 'https://graph.facebook.com/v2.3/me/tagged_places?access_token=%s'%(token)
res = requests.get(url)
data = json.loads(res.text)['data']
print "%s\t%s\t%s\t%s\t%s"%('id', 'country', 'latitude','longitude','checkins')
for entry in data:
	place = entry['place']
	location = place['location']
	res2 = requests.get('https://graph.facebook.com/v2.3/%s?access_token=%s'%(place['id'], token))
	place_detail = json.loads(res2.text)
	if 'country' in location:
		print "%s\t%s\t%s\t%s\t%s"%(place['id'], location['country'] , location['latitude'], location['longitude'], place_detail['checkins'])
