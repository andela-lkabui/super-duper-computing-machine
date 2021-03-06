import logging
import threading
import random
import concurrent.futures

class Pipeline:
    """
    Class to allow a single element pipeline between producer and consumer.
    """

    def __init__(self):
        self.message = 0
        self.producer_lock = threading.Lock()
        self.consumer_lock = threading.Lock()
        self.consumer_lock.acquire()
    
    def get_message(self, name):
        logging.debug(f'{name}:about to acquire consumer_lock')
        self.consumer_lock.acquire()

        logging.debug(f'{name}:has consumer_lock')
        message = self.message

        logging.debug(f'{name}:about to release producer_lock')
        self.producer_lock.release()

        logging.debug(f'{name}:producer_lock released')
        return message
    
    def set_message(self, message, name):
        logging.debug(f'{name}:about to acquire producer_lock')
        self.producer_lock.acquire()

        logging.debug(f'{name}:has producer_lock')
        self.message = message

        logging.debug(f'{name}:about to release consumer_lock')
        self.consumer_lock.release()

        logging.debug(f'{name}:consumer_lock released')


SENTINEL = object()

def producer(pipeline):
    """
    Pretend we are getting a message from the network.
    """
    for index in range(10):
        message = random.randint(1, 101)
        logging.info(f'Producer got message: {message}.')
        pipeline.set_message(message, 'Producer')
    # send a sentinel message to tell the consumer we are done
    pipeline.set_message(SENTINEL, 'Producer')

def consumer(pipeline):
    """
    Pretend we are saving a message to the database.
    """
    message = 0
    while message is not SENTINEL:
        message = pipeline.get_message('Consumer')
        if message is not SENTINEL:
            logging.info(f'Consumer storing message: {message}.')

if __name__ == '__main__':
    format = '%(asctime)s: %(message)s'
    logging.basicConfig(format=format, level=logging.INFO, datefmt='%H:%M:%S')
    pipeline = Pipeline()
    with concurrent.futures.ThreadPoolExecutor(max_workers=2) as executor:
        executor.submit(producer, pipeline)
        executor.submit(consumer, pipeline)
