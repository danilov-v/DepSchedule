class NotificationManagerConstructor {
  constructor() {
    this.hanlders = [];
  }

  subscribe(fn) {
    this.hanlders.push(fn);
  }

  fire(data) {
    this.hanlders.forEach(handler => {
      handler(data);
    });
  }
}

export const NotificationManager = new NotificationManagerConstructor();
