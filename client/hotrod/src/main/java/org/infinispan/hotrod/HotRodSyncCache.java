package org.infinispan.hotrod;

import static org.infinispan.hotrod.impl.Util.await;

import java.util.Map;
import java.util.Set;

import org.infinispan.api.common.CacheEntry;
import org.infinispan.api.common.CacheEntryVersion;
import org.infinispan.api.common.CacheOptions;
import org.infinispan.api.common.CacheWriteOptions;
import org.infinispan.api.common.CloseableIterable;
import org.infinispan.api.common.process.CacheProcessor;
import org.infinispan.api.common.process.CacheProcessorOptions;
import org.infinispan.api.configuration.CacheConfiguration;
import org.infinispan.api.sync.SyncCache;
import org.infinispan.api.sync.SyncCacheEntryProcessor;
import org.infinispan.api.sync.SyncQuery;
import org.infinispan.api.sync.SyncStreamingCache;
import org.infinispan.api.sync.events.cache.SyncCacheEntryListener;
import org.infinispan.hotrod.impl.cache.RemoteCache;

/**
 * @since 14.0
 **/
public class HotRodSyncCache<K, V> implements SyncCache<K, V> {
   private final HotRod hotrod;
   private final RemoteCache<K, V> remoteCache;

   HotRodSyncCache(HotRod hotrod, RemoteCache<K, V> remoteCache) {
      this.hotrod = hotrod;
      this.remoteCache = remoteCache;
   }

   @Override
   public String name() {
      return remoteCache.getName();
   }

   @Override
   public CacheConfiguration configuration() {
      return await(remoteCache.configuration());
   }

   @Override
   public HotRodSyncContainer container() {
      return hotrod.sync();
   }

   @Override
   public CacheEntry<K, V> getEntry(K key, CacheOptions options) {
      return await(remoteCache.getEntry(key, options));
   }

   @Override
   public V put(K key, V value, CacheWriteOptions options) {
      return await(remoteCache.put(key, value, options));
   }

   @Override
   public void set(K key, V value, CacheWriteOptions options) {
      await(remoteCache.set(key, value, options));
   }

   @Override
   public V putIfAbsent(K key, V value, CacheWriteOptions options) {
      return await(remoteCache.putIfAbsent(key, value, options));
   }

   @Override
   public boolean setIfAbsent(K key, V value, CacheWriteOptions options) {
      return await(remoteCache.setIfAbsent(key, value, options));
   }

   @Override
   public boolean replace(K key, V value, CacheEntryVersion version, CacheWriteOptions options) {
      return await(remoteCache.replace(key, value, version, options));
   }

   @Override
   public CacheEntry<K, V> getOrReplaceEntry(K key, V value, CacheEntryVersion version, CacheWriteOptions options) {
      return await(remoteCache.getOrReplaceEntry(key, value, version, options));
   }

   @Override
   public boolean remove(K key, CacheOptions options) {
      return await(remoteCache.remove(key, options));
   }

   @Override
   public boolean remove(K key, CacheEntryVersion version, CacheOptions options) {
      return await(remoteCache.remove(key, version, options));
   }

   @Override
   public V getAndRemove(K key, CacheOptions options) {
      return await(remoteCache.getAndRemove(key, options));
   }

   @Override
   public CloseableIterable<K> keys() {
      throw new UnsupportedOperationException();
   }

   @Override
   public CloseableIterable<K> keys(CacheOptions options) {
      throw new UnsupportedOperationException();
   }

   @Override
   public CloseableIterable<CacheEntry<K, V>> entries() {
      throw new UnsupportedOperationException();
   }

   @Override
   public CloseableIterable<CacheEntry<K, V>> entries(CacheOptions options) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void putAll(Map<K, V> entries, CacheWriteOptions options) {
      await(remoteCache.putAll(entries, options));
   }

   @Override
   public Map<K, V> getAll(Set<K> keys) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Map<K, V> getAll(Set<K> keys, CacheOptions options) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Map<K, V> getAll(CacheOptions options, K... keys) {
      return null;
   }

   @Override
   public Set<K> removeAll(Set<K> keys) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Set<K> removeAll(Set<K> keys, CacheOptions options) {
      return null;
   }

   @Override
   public Map<K, V> getAndRemoveAll(Set<K> keys) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Map<K, V> getAndRemoveAll(Set<K> keys, CacheOptions options) {
      throw new UnsupportedOperationException();
   }

   @Override
   public long estimateSize(CacheOptions options) {
      return await(remoteCache.estimateSize(options));
   }

   @Override
   public void clear(CacheOptions options) {
      await(remoteCache.clear(options));
   }

   @Override
   public <R> SyncQuery<K, V, R> query(String query, CacheOptions options) {
      return new HotRodSyncQuery(query, options);
   }

   @Override
   public AutoCloseable listen(SyncCacheEntryListener<K, V> listener) {
      throw new UnsupportedOperationException();
   }

   @Override
   public <T> Map<K, T> process(Set<K> keys, SyncCacheEntryProcessor<K, V, T> processor, CacheProcessorOptions options) {
      throw new UnsupportedOperationException();
   }

   @Override
   public <T> T processAll(CacheProcessor processor, CacheProcessorOptions options) {
      return await(remoteCache.processAll(processor, options));
   }

   @Override
   public SyncStreamingCache<K> streaming() {
      return new HotRodSyncStreamingCache(hotrod, remoteCache);
   }
}
